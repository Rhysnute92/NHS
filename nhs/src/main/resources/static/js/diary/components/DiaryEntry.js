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
                    position: relative;
                    border-bottom: 1px solid lightgrey;
                }
                
                .diary-entry:hover {
                  background-color: lightgrey;
                }
                
                .icon-container {
                  display: flex;
                  gap: 1rem;
                }
                
                .diary-entry-content svg {
                    height: 3.5rem;
                    width: auto;
                }
                
                .diary-entry-content svg, .diary-entry-content svg path {
                    fill: var(--nhs-dark-grey);
                }
                
                .diary-entry-section svg, .diary-entry-section svg path {
                    height: 2rem;
                    width: auto;
                }
                
                .diary-entry-title-container {
                    display: flex;
                    align-items: center;
                    justify-content: flex-end;
                    padding: 1rem;
                    color: var(--nhs-dark-grey);
                    width: 100%;
                }
                
                .diary-entry-title {
                    text-align: left;
                }
                
                .delete-button {
                    border: none;
                    background: none;
                    color: var(--nhs-dark-grey);
                    font-size: 2rem;
                    cursor: pointer;
                    position: absolute;
                    top: 1rem;
                    right: 1rem;
                }

                .diary-entry-icon {
                  fill: currentColor;
                }
                
                .diary-entry-content {
                    display: flex;
                    flex-direction: column;
                    padding: 1rem;
                }
                
                .mood-section div {
                    display: flex;
                    gap: 0.5rem;
                    align-items: center;  
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

                ul {
                    padding-left: 1rem;
                }

                li {
                    margin-bottom: 0.5rem;
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
                <button class="delete-button">&times;</button>
                <div class="diary-entry-content">
                    <div class="diary-entry-preview">
                        <div class="icon-container"></div>
                    </div>
                    <div class="diary-entry-full hidden">
                        <!-- Mood Section -->
                        <div class="diary-entry-section mood-section">
                          <h3>Mood</h3>
                        </div>
                        
                        <!-- Symptoms Section -->
                        <div class="diary-entry-section symptoms-section">
                            <h3>Symptoms</h3>
                            <ul></ul>
                        </div>

                        <!-- Photos Section -->
                        <div class="diary-entry-section photos-section">
                            <h3>Photos</h3>
                            <div class="diary-entry-photos-container"></div>
                        </div>

                        <!-- Measurements Section -->
                        <div class="diary-entry-section measurements-section">
                            <h3>Measurements</h3>
                            <ul></ul>
                        </div>

                        <!-- Notes Section -->
                        <div class="diary-entry-section notes-section">
                            <h3>Notes</h3>
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

        if (!window.confirm('Are you sure you want to delete this entry?')) {
            return;
        }
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
        const mood = this.getAttribute('data-mood');
        const symptoms = JSON.parse(this.getAttribute('data-symptoms') || '[]');
        const photos = JSON.parse(this.getAttribute('data-photos') || '[]');
        const measurements = JSON.parse(this.getAttribute('data-measurements') || '[]');
        const notes = this.getAttribute('data-notes');

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

        const moodContainer = document.createElement('div');
        const moodIcon = document.createElement('span');
        moodIcon.innerHTML = diaryEntryIcons.mood[mood];
        moodContainer.appendChild(moodIcon);

        const moodText = document.createElement('span');
        moodText.textContent = this.formatText(mood);
        moodContainer.appendChild(moodText);

        moodSection.appendChild(moodContainer);
    }

    renderSymptoms(symptoms) {
        const symptomsList = this.shadowRoot.querySelector('.symptoms-section ul');
        symptoms.forEach(symptom => {
            let severityText = '';
            switch (symptom.severity) {
                case 1: severityText = 'Not at all'; break;
                case 2: severityText = 'A little'; break;
                case 3: severityText = 'Quite a bit'; break;
                case 4: severityText = 'A lot'; break;
            }
            symptomsList.innerHTML += `
                <li class="diary-entry-symptom">
                    <span>${symptom.name} - </span>
                    <span>${severityText}</span>
                </li>
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
        const measurementsList = this.shadowRoot.querySelector('.measurements-section ul');
        measurements.forEach(measurement => {
            measurementsList.innerHTML += `
                <li class="diary-entry-measurement">
                    <span class="measurement-type">${measurement.type} - </span>
                    <span class="measurement-value">${measurement.value}</span>
                    <span class="measurement-unit">${measurement.unit}</span>
                </li>
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
