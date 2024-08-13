// Wait until the DOM is fully loaded
document.addEventListener('DOMContentLoaded', (event) => {

    // Get the model, open button, and close button elements
    const model = document.getElementById('myModel');
    const openaddappt = document.getElementById('openaddappt');
    const closeBtn = document.querySelector('.popup-close');

    // Function to open the model
    openaddappt.addEventListener('click', () => {
        model.style.display = 'block';
        // Set the iframe src here if needed
        document.getElementById('modelFrame').src = 'http://localhost:8080/addappointment';
    });

    // Function to close the model
    closeBtn.addEventListener('click', () => {
        model.style.display = 'none';
        // Optional: Clear the iframe content when closing
        document.getElementById('modelFrame').src = '';
    });

    // Close the model when clicking outside the model content
    window.addEventListener('click', (event) => {
        if (event.target == model) {
            model.style.display = 'none';
            // Optional: Clear the iframe content when closing
            document.getElementById('modelFrame').src = '';
        }
    });

});
