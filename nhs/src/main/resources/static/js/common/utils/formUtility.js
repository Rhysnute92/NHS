/**
 * Toggle a class on conditional content for an input based on checked state
 * @param {HTMLInputElement} input input element
 * @param {string} className class to toggle
 */

// Taken from nhsuk-frontend
export const toggleConditionalInput = (input, className) => {
    // Return without error if input or class are missing
    if (!input || !className) return;
    // Get the ID of the conditional element
    const conditionalId = input.getAttribute('aria-controls'); // aria-controls indicates an element controlled by the input
    if (conditionalId) {
        // Get the conditional element from the input data-aria-controls attribute
        const conditionalElement = document.getElementById(conditionalId);
        if (conditionalElement) {
            // Check if the logic should be inverted, e.g. show the conditional element when the input is not checked
            const invertLogic = input.getAttribute('data-invert') === 'true';

            const shouldShow = invertLogic ? !input.checked : input.checked;

            // Toggle the class on the conditional element
            if (shouldShow) {
                conditionalElement.classList.remove(className);
                input.setAttribute('aria-expanded', 'true');
            } else {
                conditionalElement.classList.add(className);
                input.setAttribute('aria-expanded', 'false');
            }
        }
    }
}