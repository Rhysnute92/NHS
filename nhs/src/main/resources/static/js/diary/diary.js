document.addEventListener('DOMContentLoaded', () => {
    fetchDiaryEntries();
});

function groupEntriesByDate(entries) {
    return entries.reduce((group, entry) => {
        const date = entry.date.split('T')[0];
        if (!group[date]) {
            group[date] = [];
        }
        group[date].push(entry);
        return group;
    }, {});
}

function fetchDiaryEntries() {
    fetch('/diary/entries')
        .then(response => response.json())
        .then(data => {
            const groupedEntries = groupEntriesByDate(data);
            const container = document.querySelector('.diary-entries-container');

            // Show or hide the "No entries" message based on the fetched data
            const noEntriesMessage = document.querySelector('.no-entries');
            if (Object.keys(groupedEntries).length === 0) {
                noEntriesMessage.style.display = 'block';
            } else {
                noEntriesMessage.style.display = 'none';

                // Append grouped diary entries
                Object.keys(groupedEntries).forEach(date => {
                    const diaryGroup = document.createElement('diary-entry-group');
                    diaryGroup.setAttribute('data-date', date);
                    diaryGroup.setAttribute('entries', JSON.stringify(groupedEntries[date]));

                    container.appendChild(diaryGroup);
                });
            }
        })
        .catch(error => console.error('Error fetching diary entries:', error));
}

function handleEntryRemoved() {
    const container = document.querySelector('.diary-entries-container');
    const noEntriesMessage = document.querySelector('.no-entries');

    // Use setTimeout to wait for the DOM to update (otherwise the length will be incorrect)
    setTimeout(() => {
        // Check if there are any remaining diary-entry-group elements
        if (container.querySelectorAll('diary-entry-group').length === 0) {
            noEntriesMessage.style.display = 'block'; // Show the "No entries" message
        }
    }, 0);
}

document.addEventListener('entryGroupRemoved', handleEntryRemoved);
