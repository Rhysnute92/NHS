import { toggleConditionalInput } from "../common/utils/formUtility.js";

const eventForm = document.querySelector('#event-form');
const eventModal = document.querySelector('.event-modal');

eventForm.addEventListener('submit', (event) => {
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


document.addEventListener('DOMContentLoaded', async () => {
    // Show/hide conditional inputs for checkboxes based on checked state
    const checkboxes = document.querySelectorAll('input[type="checkbox"]');
    checkboxes.forEach((checkbox) => {
        toggleConditionalInput(checkbox, 'conditional-hidden');
        checkbox.addEventListener('change', () => toggleConditionalInput(checkbox, 'conditional-hidden'));
    })
});