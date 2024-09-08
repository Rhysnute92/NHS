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
                    height: 2.5rem;
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
                
                .measurement-container {
                    margin-bottom: 1rem;
                }
                
                .delete-button {
                    border: none;
                    background: none;
                    color: var(--nhs-dark-grey);
                    font-size: 2rem;
                    cursor: pointer;
                    position: absolute;
                    top: 50%;
                    right: 0;
                    transform: translate(0, -50%);
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
                
                td, th {
                    padding: 0.5rem;
                    border-bottom: 1px solid lightgrey;
                    text-align: left;
                }
                
                @media (max-width: 768px) {
                    .diary-entry:hover {
                        background-color: var(--nhs-white);
                    }
                
                    .diary-entry-photos-container {
                        grid-template-columns: repeat(3, 1fr);
                    }
                
                    *:hover {
                        opacity: 100% !important;
                    }
                
                    .diary-entry-section, .diary-timestamp {
                        font-size: 1.2rem;
                    }
                    
                    .icon-container {
                        gap: 0.5rem;
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
        const mood = this.getAttribute('data-mood') !== 'null' ? this.getAttribute('data-mood') : null;
        const symptoms = JSON.parse(this.getAttribute('data-symptoms') || '[]');
        const photos = JSON.parse(this.getAttribute('data-photos') || '[]');
        const nonSidedMeasurements = JSON.parse(this.getAttribute('data-nonsidedmeasurements') || '[]');
        const twoSidedMeasurementGroups = JSON.parse(this.getAttribute('data-twosidedmeasurementgroups') || '[]');
        const notes = this.getAttribute('data-notes');
        const date = this.getAttribute('data-date');

        const fullContent = this.shadowRoot.querySelector('.diary-entry-full');
        fullContent.innerHTML = '';

        if (mood !== null) this.renderMood(mood, fullContent);
        if (symptoms.length) this.renderSymptoms(symptoms, fullContent);
        if (photos.length) this.renderPhotos(photos, fullContent);
        if (nonSidedMeasurements.length || twoSidedMeasurementGroups.length) {
            this.renderMeasurements(nonSidedMeasurements, twoSidedMeasurementGroups, fullContent);
        }
        if (notes) this.renderNotes(notes, fullContent);

        this.renderDate(date);
        this.addIcons(mood, symptoms, photos, nonSidedMeasurements, notes);
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

    renderMeasurements(nonSidedMeasurements, twoSidedMeasurementGroups, fullContent) {
        // Filter out non-sided measurements with an empty type or value of 0
        const filteredNonSidedMeasurements = nonSidedMeasurements
            .filter(measurement => measurement.type && measurement.type.trim() !== '' && measurement.value !== 0);

        // Filter out two-sided measurement groups with empty types and any locations where the type is empty
        const filteredTwoSidedMeasurementGroups = twoSidedMeasurementGroups
            .filter(group => group.type && group.type.trim() !== '')
            .map(group => {
                // Also filter locations inside the group
                const filteredLocations = group.locations.filter(location => location.location && location.location.trim() !== '');
                return { ...group, locations: filteredLocations };
            });

        // Create a section for all measurements
        const measurementsSection = document.createElement('div');
        measurementsSection.classList.add('diary-entry-section', 'measurements-section');

        // Add a title for the section
        const measurementsTitle = document.createElement('h3');
        measurementsTitle.textContent = 'Measurements';
        measurementsSection.appendChild(measurementsTitle);

        // Add non-sided measurements to the section
        if (filteredNonSidedMeasurements.length) {
            filteredNonSidedMeasurements.forEach(measurement => {
                const measurementContainer = document.createElement('div');
                measurementContainer.className = 'measurement-container';

                const measurementDiv = document.createElement('div');

                const measurementTitle = document.createElement('h4');
                measurementTitle.textContent = measurement.type;
                measurementDiv.appendChild(measurementTitle);

                const valueSpan = document.createElement('span');
                valueSpan.textContent = measurement.value;
                measurementDiv.appendChild(valueSpan);

                const unitSpan = document.createElement('span');
                unitSpan.textContent = ` ${measurement.unit}`;
                measurementDiv.appendChild(unitSpan);

                measurementContainer.appendChild(measurementDiv);
                measurementsSection.appendChild(measurementContainer);
            });
        }

        // Add two-sided measurement groups to the section
        if (filteredTwoSidedMeasurementGroups.length) {
            filteredTwoSidedMeasurementGroups.forEach(group => {
                const measurementContainer = document.createElement('div');
                measurementContainer.className = 'measurement-container';

                const groupTitle = document.createElement('h4');
                groupTitle.textContent = group.type;
                measurementsSection.appendChild(groupTitle);

                const table = document.createElement('table');
                const thead = document.createElement('thead');
                const trHeader = document.createElement('tr');

                // Create the header row for the table
                trHeader.innerHTML = `
                <th>Location</th>
                <th>Left Side</th>
                <th>Right Side</th>
            `;
                thead.appendChild(trHeader);
                table.appendChild(thead);

                const tbody = document.createElement('tbody');

                // Populate the table with two-sided measurements
                group.locations.forEach(location => {
                    const tr = document.createElement('tr');

                    const locationCell = document.createElement('td');
                    locationCell.textContent = location.location || 'Unknown Location';
                    tr.appendChild(locationCell);

                    const leftCell = document.createElement('td');
                    leftCell.textContent = location.leftMeasurement
                        ? `${location.leftMeasurement.value}${location.leftMeasurement.unit}`
                        : '-';
                    tr.appendChild(leftCell);

                    const rightCell = document.createElement('td');
                    rightCell.textContent = location.rightMeasurement
                        ? `${location.rightMeasurement.value}${location.rightMeasurement.unit}`
                        : '-';
                    tr.appendChild(rightCell);

                    tbody.appendChild(tr);
                });

                table.appendChild(tbody);

                measurementContainer.appendChild(table);
                measurementsSection.appendChild(measurementContainer);
            });
        }

        // Append the combined measurements section to the full content
        fullContent.appendChild(measurementsSection);
    }


    groupMeasurementsByType(measurements) {
        const groupedMeasurements = [];

        measurements.forEach(measurement => {
            // Find existing group or create a new one
            let group = groupedMeasurements.find(g => g.type === measurement.type);
            if (!group) {
                group = { type: measurement.type, locations: [] };
                groupedMeasurements.push(group);
            }

            // Find or create location entry
            let locationEntry = group.locations.find(l => l.location === measurement.location);
            if (!locationEntry) {
                locationEntry = { location: measurement.location, leftMeasurement: null, rightMeasurement: null };
                group.locations.push(locationEntry);
            }

            // Assign the measurement to the correct side
            if (measurement.side === 'left') {
                locationEntry.leftMeasurement = measurement;
            } else if (measurement.side === 'right') {
                locationEntry.rightMeasurement = measurement;
            }
        });

        return groupedMeasurements;
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
