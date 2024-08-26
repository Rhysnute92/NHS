class DiaryEntryGroup extends HTMLElement {
    constructor() {
        super();
        const shadow = this.attachShadow({ mode: 'open' });

        shadow.innerHTML = `
            <style>
                .diary-entry-group {
                    margin-bottom: 2rem;
                }
                
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
            diaryEntry.setAttribute('data-mood', entry.mood);
            diaryEntry.setAttribute('data-symptoms', JSON.stringify(entry.symptoms));
            diaryEntry.setAttribute('data-photos', JSON.stringify(entry.photos));
            diaryEntry.setAttribute('data-measurements', JSON.stringify(entry.measurements));
            diaryEntry.setAttribute('data-notes', entry.notes);

            // Append the entry to the container
            entryContainer.appendChild(diaryEntry);
        });
    }
}

customElements.define('diary-entry-group', DiaryEntryGroup);
