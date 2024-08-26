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

const diaryEntries = document.querySelectorAll('.diary-entry');
diaryEntries.forEach(entry => {
    entry.addEventListener('click', (event) => {
        const preview = entry.querySelector('.diary-entry-preview');
        const full = entry.querySelector('.diary-entry-full');
        preview.classList.toggle('hidden');
        full.classList.toggle('hidden');
    });

});

let measurementTypes = document.querySelectorAll('.measurement-type');
measurementTypes.forEach((type) => {
    let text = type.textContent;
    text = text.toLowerCase();
    text = text.charAt(0).toUpperCase() + text.slice(1);
    type.textContent = text;
});

document.addEventListener('DOMContentLoaded', () => {
    fetchDiaryEntries();
});
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

            Object.keys(groupedEntries).forEach(date => {
                const diaryGroup = document.createElement('diary-entry-group');
                diaryGroup.setAttribute('data-date', date);
                diaryGroup.setAttribute('entries', JSON.stringify(groupedEntries[date]));

                container.appendChild(diaryGroup);
            });
        })
        .catch(error => console.error('Error fetching diary entries:', error));
}
