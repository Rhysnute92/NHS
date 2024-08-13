document.addEventListener('DOMContentLoaded', (event) => {
    // Get the model, open button, and close button elements
    const model = document.getElementById('myModel');
    const openaddappt = document.getElementById('openaddappt');
    const closeBtn = document.querySelector('.popup-close');
    const modelContent = document.getElementById('modelContent');

    // Function to open the model and load content
    openaddappt.addEventListener('click', () => {
        model.style.display = 'block';
        fetch('/addappointment') // Fetch the HTML fragment
            .then(response => response.text())
            .then(data => {
                modelContent.innerHTML = data; // Insert the fragment into the model content
            })
            .catch(error => console.error('Error loading content:', error));
    });

    // Function to close the model
    closeBtn.addEventListener('click', () => {
        model.style.display = 'none';
        modelContent.innerHTML = ''; // Clear content when closing
    });

    // Close the model when clicking outside the model content
    window.addEventListener('click', (event) => {
        if (event.target == model) {
            model.style.display = 'none';
            modelContent.innerHTML = ''; // Clear content when closing
        }
    });
});
