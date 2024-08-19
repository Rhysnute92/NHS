import { toggleConditionalInput } from "../common/utils/formUtility.js";

const eventForm = document.querySelector('#eventForm');
const eventModal = document.querySelector('.event-modal');
eventForm.addEventListener('submit', (event) => {
    event.preventDefault();

    // Get the submit button and disable it to prevent multiple submissions
    const submitButton = eventForm.querySelector('button[type="submit"]');
    submitButton.disabled = true;

    const symptomCheckboxes = eventForm.querySelectorAll('input[type="checkbox"]');
    let index = 0;

    let formData = new FormData(eventForm);

    // Iterate through symptom checkboxes to find checked symptoms
    symptomCheckboxes.forEach(checkbox => {
        if (checkbox.checked) {
            const symptomName = checkbox.name;

            // Find the severity radio buttons corresponding to this symptom and get the selected value
            const severityRadios = eventForm.querySelectorAll(`input[name="${symptomName}-severity"]`);
            severityRadios.forEach(radio => {
                if (radio.checked) {
                    // Add the symptom to the form data
                    formData.set(`symptoms[${index}].name`, symptomName);
                    formData.set(`symptoms[${index}].severity`, radio.value);
                }
            });

            // Increment the index for the next symptom
            index++;
        }
    });

    // Submit the form data to the server
    fetch('/diary/events', {
        method: 'POST',
        body: formData
    })
        .then(response => response.json())
        .then(data => {
            const event = data.event;
            eventModal.hide();

            const eventElement = document.createElement('event-card');
            eventElement.setAttribute('event-data', JSON.stringify(event));

            document.querySelector('.event-list').appendChild(eventElement);

            // Re-enable the submit button
            submitButton.disabled = false;
        })
        .catch((error) => {
            alert('An error occurred while saving the event. Please try again.');

            // Re-enable the submit button
            submitButton.disabled = false;
        });
});


document.addEventListener('DOMContentLoaded', async () => {
    // Show/hide conditional inputs for checkboxes based on checked state
    const checkboxes = document.querySelectorAll('input[type="checkbox"]');
    checkboxes.forEach((checkbox) => {
        toggleConditionalInput(checkbox, 'conditional-hidden');
        checkbox.addEventListener('change', () => toggleConditionalInput(checkbox, 'conditional-hidden'));
    })
});