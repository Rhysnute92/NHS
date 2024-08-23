import { diaryEntryIcons } from "../diaryMappings.js";

class DiaryEntry extends HTMLElement {
    constructor() {
        super();

        const shadow = this.attachShadow({ mode: 'open' });

        shadow.innerHTML = `
            <style>
                * {
                    box-sizing: border-box;
                    margin: 0;
                    padding: 0;
                }
                
                .diary-entry {
                    /*border-radius: 0.4rem;*/
                    color: var(--nhs-black);
                    /*box-shadow: 2px 2px 0.4rem var(--nhs-dark-grey);*/
                    overflow: hidden;
                    width: 100%;
                    background-color: var(--nhs-white);
                    margin-bottom: 1rem;
                }
                
                .icon-container {
                  display: flex;
                  gap: 0.5rem;
                }
                
                .diary-entry-content svg {
                    height: 3.5rem;
                    width: auto;
                }
                
                .diary-entry-content svg, .diary-entry-content svg path {
                    fill: currentColor !important;
                }
                
                .diary-entry-title-container {
                    display: flex;
                    align-items: center;
                    justify-content: space-between;
                    padding: 1rem;
                    background-color: var(--nhs-bright-blue);
                    color: white;
                    width: 100%;
                }
                
                .diary-entry-title {
                    text-align: left;
                }
                
                .delete-button {
                    border: none;
                    background: none;
                    color: white;
                    font-size: 2rem;
                }

                .diary-entry-icon {
                  fill: currentColor;
                }
                
                .diary-entry-content {
                    display: flex;
                    flex-direction: column;
                    padding: 1rem;
                }
                
                .diary-entry-full {
                    display: flex;
                    flex-direction: column;
                    gap: 1rem;
                }
                
                .diary-entry-full.hidden {
                    height: 0;
                    overflow: hidden;
                }
                
                .diary-entry-preview {
                    display: flex;
                    gap: 1rem;
                    font-size: 2rem;
                    color: var(--nhs-dark-grey);
                }
                
                .diary-entry-preview.hidden {
                    height: 0;
                    overflow: hidden;
                }
                
                .diary-entry-photos-container {
                    display: grid;
                    grid-template-columns: repeat(5, 0.8fr);
                    gap: 0.5rem;
                }
                
                .diary-entry-photo {
                    width: 100%;
                    height: 100%;
                    object-fit: cover;
                    aspect-ratio: 1 / 1;
                }
                
                .diary-entry-section {
                    display: flex;
                    flex-direction: column;
                    gap: 1rem;
                }
                
                .diary-entry-symptoms {
                    display: flex;
                    flex-direction: column;
                    gap: 0.5rem;
                }
                
                
                .measurement-unit {
                    text-transform: lowercase;
                }
                
                @media (max-width: 768px) {
                    .diary-entry-photos-container {
                        grid-template-columns: repeat(3, 1fr);
                    }
                
                    *:hover {
                        opacity: 100% !important;
                    }
                
                    .diary-entry-section {
                        font-size: 1.2rem;
                    }
                }
            </style>
            <div class="diary-entry">
                <div class="diary-entry-title-container">
                    <h2 class="diary-entry-title"></h2>
                    <button class="delete-button">&times;</button>
                </div>
                <div class="diary-entry-content">
                    <div class="diary-entry-preview">
                        <div class="icon-container"></div>
                    </div>
                    <div class="diary-entry-full hidden">
                        <!-- Mood Section -->
                        <div class="diary-entry-section mood-section"></div>
                        
                        <!-- Symptoms Section -->
                        <div class="diary-entry-section symptoms-section"></div>

                        <!-- Photos Section -->
                        <div class="diary-entry-section photos-section">
                            <div class="diary-entry-photos-container"></div>
                        </div>

                        <!-- Measurements Section -->
                        <div class="diary-entry-section measurements-section"></div>

                        <!-- Notes Section -->
                        <div class="diary-entry-section notes-section">
                            <p class="diary-entry-notes"></p>
                        </div>
                    </div>
                </div>
            </div>
        `;

        this.bindEvents();
    }

    connectedCallback() {
        this.render();
    }

    bindEvents() {
        const deleteButton = this.shadowRoot.querySelector('.delete-button');
        deleteButton.addEventListener('click', this.handleDelete.bind(this));

        const diaryEntry = this.shadowRoot.querySelector('.diary-entry');
        diaryEntry.addEventListener('click', this.toggleDetails.bind(this));
    }

    handleDelete(event) {
        event.stopPropagation();
        const entryId = this.getAttribute('data-id');
        fetch(`/diary/delete/${entryId}`, { method: 'DELETE' })
            .then(response => {
                if (response.ok) {
                    this.remove();
                } else {
                    console.error('Failed to delete entry');
                }
            })
            .catch(error => console.error('Error:', error));
    }

    toggleDetails(event) {
        const preview = this.shadowRoot.querySelector('.diary-entry-preview');
        const full = this.shadowRoot.querySelector('.diary-entry-full');
        preview.classList.toggle('hidden');
        full.classList.toggle('hidden');
    }

    render() {
        const dateStr = this.getAttribute('data-date');
        const mood = this.getAttribute('data-mood');
        const symptoms = JSON.parse(this.getAttribute('data-symptoms') || '[]');
        const photos = JSON.parse(this.getAttribute('data-photos') || '[]');
        const measurements = JSON.parse(this.getAttribute('data-measurements') || '[]');
        const notes = this.getAttribute('data-notes');

        // Format the date
        const date = new Date(dateStr);
        const options = { weekday: "long", day: '2-digit', month: 'long' };
        this.shadowRoot.querySelector('.diary-entry-title').textContent = date.toLocaleDateString('en-GB', options);

        // Render the mood, symptoms, photos, measurements, and notes
        this.renderMood(mood);
        this.renderSymptoms(symptoms);
        this.renderPhotos(photos);
        this.renderMeasurements(measurements);
        this.renderNotes(notes);

        // Add icons for the sections that have content
        this.addIcons(mood, symptoms, photos, measurements, notes);
    }

    addIcons(mood, symptoms, photos, measurements, notes) {
        const iconContainer = this.shadowRoot.querySelector('.icon-container');
        if (mood) iconContainer.innerHTML += diaryEntryIcons.mood[mood];
        if (symptoms.length > 0) iconContainer.innerHTML += diaryEntryIcons.symptoms;
        if (photos.length > 0) iconContainer.innerHTML += diaryEntryIcons.photos;
        if (measurements.length > 0) iconContainer.innerHTML += diaryEntryIcons.measurements;
        if (notes) iconContainer.innerHTML += diaryEntryIcons.notes;
    }

    renderMood(mood) {
        const moodSection = this.shadowRoot.querySelector('.mood-section');
        const moodIcon = document.createElement('svg');
        moodIcon.innerHTML = diaryEntryIcons.mood[mood];
        moodSection.appendChild(moodIcon);

        const moodText = document.createElement('span');
        moodText.textContent = this.formatText(mood);
        moodSection.appendChild(moodText);
    }

    renderSymptoms(symptoms) {
        const symptomsSection = this.shadowRoot.querySelector('.symptoms-section');
        symptoms.forEach(symptom => {
            let severityText = '';
            switch (symptom.severity) {
                case 1: severityText = 'Not at all'; break;
                case 2: severityText = 'A little'; break;
                case 3: severityText = 'Quite a bit'; break;
                case 4: severityText = 'A lot'; break;
            }
            symptomsSection.innerHTML += `
                <div class="diary-entry-symptom">
                    <h4>${symptom.name}</h4>
                    <span>${severityText}</span>
                </div>
            `;
        });
    }

    renderPhotos(photos) {
        const photosContainer = this.shadowRoot.querySelector('.diary-entry-photos-container');
        photos.forEach(photo => {
            photosContainer.innerHTML += `<img src="/files/${photo.url}" class="diary-entry-photo" />`;
        });
    }

    renderMeasurements(measurements) {
        const measurementsSection = this.shadowRoot.querySelector('.measurements-section');
        measurements.forEach(measurement => {
            measurementsSection.innerHTML += `
                <div class="diary-entry-measurement">
                    <h4 class="measurement-type">${measurement.type}</h4>
                    <span class="measurement-value">${measurement.value}</span>
                    <span class="measurement-unit">${measurement.unit}</span>
                </div>
            `;
        });

        // Format measurement type text (capitalise first letter)
        const measurementTypes = this.shadowRoot.querySelectorAll('.measurement-type');

        measurementTypes.forEach(type => {
            let text = type.textContent.toLowerCase();
            type.textContent = this.formatText(text);
        });
    }

    formatText(text) {
        return text.charAt(0).toUpperCase() + text.slice(1).toLowerCase();
    }


    renderNotes(notes) {
        const notesSection = this.shadowRoot.querySelector('.diary-entry-notes');
        notesSection.textContent = notes || '';
    }
}

customElements.define('diary-entry', DiaryEntry);
