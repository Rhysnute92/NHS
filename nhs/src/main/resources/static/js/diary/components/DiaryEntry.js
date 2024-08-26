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
                    color: var(--nhs-black);
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
                    height: 3rem;
                    width: auto;
                }
                
                .diary-entry-content svg, .diary-entry-content svg path {
                    fill: var(--nhs-dark-grey) !important;
                }
                
                .diary-entry-section svg, .diary-entry-section svg path {
                    height: 2rem;
                    width: auto;
                }
                
                .diary-entry-section h3 {
                    margin-bottom: 1rem;
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
                    font-size: 2.5rem;
                    cursor: pointer;
                    position: absolute;
                    top: 50%;
                    right: 0;
                    transform: translate(-50%, -50%);
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
                    margin-top: 2rem;
                }
                
                .diary-entry-full.hidden {
                    height: 0;
                    overflow: hidden;
                    transition: height 0.3s;
                    margin-top: 0;
                }
                
                .diary-entry-preview {
                    display: flex;
                    gap: 1rem;
                    font-size: 1.5rem;
                    color: var(--nhs-mid-grey);
                    align-items: center;
                    position: relative;
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
                
                .mood-section div {
                    display: flex;
                    gap: 0.5rem;
                    align-items: center;
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
                <div class="diary-entry-content">
                    <div class="diary-entry-preview">
                      <div class="diary-timestamp"></div>
                      <div class="icon-container"></div>
                      <button class="delete-button">&times;</button>
                    </div>
                    <div class="diary-entry-full hidden">
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
                    this.dispatchEvent(new CustomEvent('entryRemoved', { bubbles: true, composed: true }));
                } else {
                    console.error('Failed to delete entry');
                }
            })
            .catch(error => console.error('Error:', error));
    }

    toggleDetails(event) {
        const full = this.shadowRoot.querySelector('.diary-entry-full');
        full.classList.toggle('hidden');
    }

    render() {
        const mood = this.getAttribute('data-mood');
        const symptoms = JSON.parse(this.getAttribute('data-symptoms') || '[]');
        const photos = JSON.parse(this.getAttribute('data-photos') || '[]');
        const measurements = JSON.parse(this.getAttribute('data-measurements') || '[]');
        const notes = this.getAttribute('data-notes');
        const date = this.getAttribute('data-date');

        // Clear full entry content
        const fullContent = this.shadowRoot.querySelector('.diary-entry-full');
        fullContent.innerHTML = '';

        // Render the mood, symptoms, photos, measurements, and notes
        if (mood) this.renderMood(mood, fullContent);
        if (symptoms.length) this.renderSymptoms(symptoms, fullContent);
        if (photos.length) this.renderPhotos(photos, fullContent);
        if (measurements.length) this.renderMeasurements(measurements, fullContent);
        if (notes) this.renderNotes(notes, fullContent);

        this.renderDate(date);

        // Add icons for the sections that have content
        this.addIcons(mood, symptoms, photos, measurements, notes);
    }

    addIcons(mood, symptoms, photos, measurements, notes) {
        const iconContainer = this.shadowRoot.querySelector('.icon-container');
        iconContainer.innerHTML = ''; // Clear existing icons
        if (mood) iconContainer.innerHTML += diaryEntryIcons.mood[mood];
        if (symptoms.length > 0) iconContainer.innerHTML += diaryEntryIcons.symptoms;
        if (photos.length > 0) iconContainer.innerHTML += diaryEntryIcons.photos;
        if (measurements.length > 0) iconContainer.innerHTML += diaryEntryIcons.measurements;
        if (notes) iconContainer.innerHTML += diaryEntryIcons.notes;
    }

    renderDate(date) {
        const diaryTimestamp = this.shadowRoot.querySelector('.diary-timestamp');
        if (date) {
            const formattedDate = new Date(date).toLocaleString('en-GB', {
                hour: '2-digit',
                minute: '2-digit'
            });
            diaryTimestamp.textContent = formattedDate;
        }
    }

    renderMood(mood, fullContent) {
        const moodSection = document.createElement('div');
        moodSection.classList.add('diary-entry-section', 'mood-section');

        const moodTitle = document.createElement('h3');
        moodTitle.textContent = 'Mood';
        moodSection.appendChild(moodTitle);

        const moodContainer = document.createElement('div');
        const moodIcon = document.createElement('span');
        moodIcon.innerHTML = diaryEntryIcons.mood[mood];
        moodContainer.appendChild(moodIcon);

        const moodText = document.createElement('span');
        moodText.textContent = this.formatText(mood);
        moodContainer.appendChild(moodText);

        moodSection.appendChild(moodContainer);
        fullContent.appendChild(moodSection);
    }

    renderSymptoms(symptoms, fullContent) {
        const symptomsSection = document.createElement('div');
        symptomsSection.classList.add('diary-entry-section', 'symptoms-section');

        const symptomsTitle = document.createElement('h3');
        symptomsTitle.textContent = 'Symptoms';
        symptomsSection.appendChild(symptomsTitle);

        const symptomsList = document.createElement('ul');
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
        symptomsSection.appendChild(symptomsList);
        fullContent.appendChild(symptomsSection);
    }

    renderPhotos(photos, fullContent) {
        const photosSection = document.createElement('div');
        photosSection.classList.add('diary-entry-section', 'photos-section');

        const photosTitle = document.createElement('h3');
        photosTitle.textContent = 'Photos';
        photosSection.appendChild(photosTitle);

        const photosContainer = document.createElement('div');
        photosContainer.classList.add('diary-entry-photos-container');

        photos.forEach(photo => {
            photosContainer.innerHTML += `<img src="/files/${photo.url}" class="diary-entry-photo" />`;
        });

        photosSection.appendChild(photosContainer);
        fullContent.appendChild(photosSection);
    }

    renderMeasurements(measurements, fullContent) {
        const measurementsSection = document.createElement('div');
        measurementsSection.classList.add('diary-entry-section', 'measurements-section');

        const measurementsTitle = document.createElement('h3');
        measurementsTitle.textContent = 'Measurements';
        measurementsSection.appendChild(measurementsTitle);

        const measurementsList = document.createElement('ul');
        measurements.forEach(measurement => {
            measurementsList.innerHTML += `
                <li class="diary-entry-measurement">
                    <span class="measurement-type">${this.formatText(measurement.type)} - </span>
                    <span class="measurement-value">${measurement.value}</span>
                    <span class="measurement-unit">${measurement.unit}</span>
                </li>
            `;
        });

        measurementsSection.appendChild(measurementsList);
        fullContent.appendChild(measurementsSection);
    }

    renderNotes(notes, fullContent) {
        const notesSection = document.createElement('div');
        notesSection.classList.add('diary-entry-section', 'notes-section');

        const notesTitle = document.createElement('h3');
        notesTitle.textContent = 'Notes';
        notesSection.appendChild(notesTitle);

        const notesContent = document.createElement('p');
        notesContent.classList.add('diary-entry-notes');
        notesContent.textContent = notes || '';

        notesSection.appendChild(notesContent);
        fullContent.appendChild(notesSection);
    }

    formatText(text) {
        return text.charAt(0).toUpperCase() + text.slice(1).toLowerCase();
    }
}

customElements.define('diary-entry', DiaryEntry);
