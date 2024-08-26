document.addEventListener('DOMContentLoaded', (event) => {
    const dateElements = document.querySelectorAll('.diary-entry-title');
    dateElements.forEach(element => {
        const dateStr = element.getAttribute('data-date');
        console.log(dateStr)
        const date = new Date(dateStr);
        const options = { weekday: "long", day: '2-digit', month: 'long' };
        const formattedDate = date.toLocaleDateString('en-GB', options);
        element.textContent = formattedDate;
    });
});


document.addEventListener('DOMContentLoaded', () => {
    createNoEntriesMessage();
    fetchDiaryEntries();
});

function createNoEntriesMessage() {
    const container = document.querySelector('.diary-entries-container');
    const noEntriesMessage = document.querySelector('.no-entries') || document.createElement('p');
    noEntriesMessage.textContent = 'No diary entries yet';
    noEntriesMessage.classList.add('no-entries');
    noEntriesMessage.style.display = 'none';
    container.appendChild(noEntriesMessage);
}

function groupEntriesByDate(entries) {
    return entries.reduce((acc, entry) => {
        const date = entry.date.split('T')[0];
        if (!acc[date]) {
            acc[date] = [];
        }
        acc[date].push(entry);
        return acc;
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
    console.log('Entry removed')
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

// Assuming the 'diary-entry-group' is responsible for removing itself from the DOM,
// you should also listen for the removal and call `handleEntryRemoved` when that happens
document.addEventListener('entryGroupRemoved', handleEntryRemoved);
