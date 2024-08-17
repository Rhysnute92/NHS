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
        const event = this.event;

        const date = event.date;

        // Use defaults for missing data
        const symptoms = Array.isArray(event.symptoms) ? event.symptoms : [];
        const treatments = Array.isArray(event.treatments) ? event.treatments : [];

        this.shadowRoot.innerHTML = `
            <style>
                * {
                    box-sizing: border-box;
                    padding: 0;
                    margin: 0;
                }
                
                :host {
                    width: 100%;
                }
                
                .event {
                    border-radius: 8px;
                    padding: 1rem;
                    margin: 1rem 0;
                    background-color: var(--nhs-white);
                }
                
                .event-header {
                    display: flex;
                    justify-content: space-between;
                    align-items: center;
                    margin-bottom: 12px;
                }
                
                .event-header h3 {
                    margin: 0;
                }
                
                .delete-event {
                    background: transparent;
                    border: none;
                    font-size: 2rem;
                    cursor: pointer;
                }
                
                .event-body {
                    display: flex;
                    justify-content: space-between;
                }
                
                .event-symptoms, .event-treatments {
                    flex: 1;
                    margin-right: 1rem;
                }
                
                .event-symptoms h4, .event-treatments h4 {
                    margin-top: 0;
                }
            </style>
            <div class="event">
                <div class="event-header">
                    <h3>${date}</h3>
                    <button class="delete-event">&times;</button>
                </div>
                <div class="event-body">
                    <div class="event-symptoms">
                        <h4>Symptoms</h4>
                        <ul>
                            <!-- Create a list item for each symptom, handling missing data -->
                            ${symptoms.length > 0 ? symptoms.map(symptom => `<li>${symptom.name || 'N/A'} - ${symptom.severity || 'N/A'}</li>`).join('') : '<li>No symptoms reported</li>'}
                        </ul>
                    </div>
                    <div class="event-treatments">
                        <h4>Treatments</h4>
                        <ul>
                            <!-- Create a list item for each treatment, handling missing data -->
                            ${treatments.length > 0 ? treatments.map(treatment => `<li>${treatment.type || 'N/A'}</li>`).join('') : '<li>No treatments reported</li>'}
                        </ul>
                    </div>
                </div>
            </div>
        `;

        // Add delete event listener
        this.shadowRoot.querySelector('.delete-event').addEventListener('click', () => this.removeEvent());
    }

    removeEvent() {
        // TODO: Implement delete event functionality
        this.remove();
    }
}

customElements.define('event-card', EventCard);
