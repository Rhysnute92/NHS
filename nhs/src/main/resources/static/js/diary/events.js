const eventForm = document.getElementById('eventForm');
const eventModal = document.getElementById('eventModal');
eventForm.addEventListener('submit', async (event) => {
    event.preventDefault();

    const formData = new FormData(eventForm);
    const response = await fetch('/diary/events', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(Object.fromEntries(formData)),
    });

    const data = await response.json();

    if (response.ok) {
        const eventItem = document.createElement('div');
        eventItem.classList.add('event-item');
        eventItem.innerHTML = `
        <h4>${data.event.eventDate}</h4>
        <p>${data.event.symptoms}</p>
        <p>${data.event.severity}</p>
        <p>${data.event.duration}</p>
        <p>${data.event.treatment}</p>
      `;
        document.getElementById('eventList').appendChild(eventItem);
        eventModal.style.display = 'none';
        eventForm.reset();
    } else {
        alert(data.message);
    }
});


document.getElementById('treatment').addEventListener('change', () => {
    document.querySelector('.treatment-details').style.display = !document.getElementById('treatment').checked ? 'block' : 'none';
});

document.querySelector('.treatment-details').style.display = !document.getElementById('treatment').checked   ? 'block' : 'none';

document.getElementById('antibiotics').addEventListener('change', () => {
    document.querySelector('.antibiotics-details').style.display = document.getElementById('antibiotics').checked ? 'block' : 'none';
});

document.querySelector('.antibiotics-details').style.display = document.getElementById('antibiotics').checked ? 'block' : 'none';
