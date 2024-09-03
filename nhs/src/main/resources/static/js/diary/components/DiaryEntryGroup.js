class DiaryEntryGroup extends HTMLElement {
    constructor() {
        super();
        const shadow = this.attachShadow({ mode: 'open' });

        shadow.innerHTML = `
            <style>                
                .diary-entry-group-title {
                    background-color: var(--nhs-bright-blue);
                    color: white;
                    padding: 1rem;
                    margin: 0;
                }
            </style>
            <div class="diary-entry-group">
                <h2 class="diary-entry-group-title"></h2>
                <div class="diary-entry-container"></div>
            </div>
        `;
    }

    connectedCallback() {
        const dateStr = this.getAttribute('data-date');
        const date = new Date(dateStr);
        const options = { weekday: "long", day: '2-digit', month: 'long' };
        this.shadowRoot.querySelector('.diary-entry-group-title').textContent = date.toLocaleDateString('en-GB', options);

        this.renderEntries();
    }

    renderEntries() {
        const entries = JSON.parse(this.getAttribute('entries'));
        const entryContainer = this.shadowRoot.querySelector('.diary-entry-container');

        // Create a diary entry component for each entry
        entries.forEach(entry => {
            const diaryEntry = document.createElement('diary-entry');
            diaryEntry.setAttribute('data-id', entry.id);
            diaryEntry.setAttribute('data-date', entry.date);
            diaryEntry.setAttribute('data-mood', entry.mood);
            diaryEntry.setAttribute('data-symptoms', JSON.stringify(entry.symptoms));
            diaryEntry.setAttribute('data-photos', JSON.stringify(entry.photos));
            // diaryEntry.setAttribute('data-measurements', JSON.stringify(entry.measurements));
            diaryEntry.setAttribute('data-nonsidedmeasurements', JSON.stringify(entry.nonSidedMeasurements || []));
            diaryEntry.setAttribute('data-twosidedmeasurementgroups', JSON.stringify(entry.twoSidedMeasurementGroups || []));
            diaryEntry.setAttribute('data-notes', entry.notes);

            // Listen for removal events from diary entry components
            diaryEntry.addEventListener('entryRemoved', this.handleEntryRemoved.bind(this));

            // Append the entry to the container
            entryContainer.appendChild(diaryEntry);
        });
    }

    handleEntryRemoved(event) {
        const entry = event.target;
        entry.remove(); // Remove the entry from the DOM

        const entryContainer = this.shadowRoot.querySelector('.diary-entry-container');

        // Check if all entries are removed
        if (entryContainer.children.length === 0) {

            // Dispatch an event to notify that this group was removed
            this.dispatchEvent(new CustomEvent('entryGroupRemoved', { bubbles: true, composed: true }));
            this.remove(); // Remove the group from the DOM
        }
    }

}

customElements.define('diary-entry-group', DiaryEntryGroup);
