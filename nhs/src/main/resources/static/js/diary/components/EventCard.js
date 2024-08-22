import { patientTreatmentText, providerTreatmentText, symptomNameText, symptomSeverityText } from '../textMappings.js';

class EventCard extends HTMLElement {
    constructor() {
        super();
        this.attachShadow({ mode: 'open' });
    }

    static get observedAttributes() {
        return ['event-data'];
    }

    attributeChangedCallback(name, oldValue, newValue) {
        if (name === 'event-data') {
            try {
                this.event = JSON.parse(newValue);
                this.render(); // Re-render when the event data changes
            } catch (e) {
                console.error('Invalid JSON passed to event-data attribute:', e);
            }
        }
    }

    connectedCallback() {
        if (this.event) {
            this.render();
        }
    }

    render() {
        if (window.location.href.includes('patient')) {
            this.treatmentText = providerTreatmentText;
        } else {
            this.treatmentText = patientTreatmentText;
        }

        const event = this.event;
        const date = event.date;

        // Use defaults for missing data
        const symptoms = Array.isArray(event.symptoms) ? event.symptoms : [];
        const treatments = Array.isArray(event.treatments) ? event.treatments : [];

        this.shadowRoot.innerHTML = `
            <link rel="stylesheet" href="/css/diary/eventCard.css">
            <div class="event">
                <div class="event-header">
                    <h3>${date || 'Date not available'}</h3>
                    <button class="delete-event-btn">&times;</button>
                </div>
                <div class="event-body">
                    <div class="event-duration">
                        <h4>Duration</h4>
                        <p>${event.duration || 'N/A'} days</p>
                    </div>
                    <div class="event-symptoms">
                        <h4>Symptoms</h4>
                        <ul>
                            ${symptoms.length > 0
                                ? symptoms.map(symptom => `<li>${symptomNameText[symptom.name] || 'N/A'} - ${symptomSeverityText[symptom.severity] || 'N/A'}</li>`).join('')
                                : '<li>No symptoms reported</li>'
                            }
                        </ul>
                    </div>
                    <div class="event-treatments">
                        <h4>Treatments</h4>
                        <ul>
                            ${treatments.length > 0
                                ? treatments.map(treatment =>
                                    `<li>
                                    ${this.treatmentText[treatment.type] || 'N/A'}
                                    ${treatment.details && treatment.details.trim() !== '' ? ` (${treatment.details})` : ''}
                                </li>`
                                ).join('')
                                : '<li>No treatments reported</li>'
                            }
                        </ul>
                    </div>
                </div>
            </div>
        `;

        // Add delete event listener
        this.deleteButton = this.shadowRoot.querySelector('.delete-event-btn');
        this.deleteButton.addEventListener('click', () => this.confirmDelete());

        this.deleteButton.style.display = window.location.href.includes('patient') ? 'none' : 'block';

    }

    confirmDelete() {
        // Show a confirmation dialog to the user
        const confirmed = window.confirm('Are you sure you want to delete this event?');

        if (confirmed) {
            // If user confirms, remove the event
            this.removeEvent();
        } else {
            console.log('Event deletion canceled.');
        }
    }

    async removeEvent() {
        const eventId = this.event.id;

        // Make a delete request to the server
        try {
            const response = await fetch(`/diary/events/${eventId}`, {
                method: 'DELETE',
                headers: {
                    'Content-Type': 'application/json'
                }
            });

            if (response.ok) {
                // If the request was successful, remove the element from the DOM
                this.remove();

            } else {
                console.error(`Failed to delete event: ${eventId}.`);
            }
        } catch (error) {
            console.error('Error occurred while deleting the event:', error);
        }
    }
}

customElements.define('event-card', EventCard);
