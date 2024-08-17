import { toggleConditionalInput } from "../common/utils/formUtility.js";

const eventForm = document.querySelector('#event-form');
const eventModal = document.querySelector('.event-modal');

eventForm.addEventListener('submit', (event) => {
    event.preventDefault();

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
            console.log('Success:', data);
            const event = data.event;
            eventModal.hide();

        })
        .catch((error) => {
            console.error('Error:', error);
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