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

    // Handle form submission
    document.getElementById('appointmentForm').addEventListener('submit', function(event) {
        event.preventDefault(); // Prevent the default form submission behavior

        // Extract form data
        const date = document.getElementById('apptDate').value;
        const time = document.getElementById('apptTime').value;
        const location = document.getElementById('apptLocation').value;
        const type = document.getElementById('apptType').value;

        // Create an appointment object
        const appointment = {
            date: new Date(`${date}T${time}`),
            location: location,
            type: type
        };

        // Add appointment to the array
        appointments.push(appointment);

        // Update the calendar to reflect the new appointment
        showCalendar(currentMonth, currentYear);

        // Hide the modal or close the form
        modal.style.display = "none";

        // Show success toast message
        const toast = document.getElementById('toast');
        toast.className = "toast show";

        // Hide the toast after 3 seconds
        setTimeout(function() {
            toast.className = toast.className.replace("show", "");
        }, 3000);

        // Redirect to a specific page
        window.location.href = "/appointments"; // Replace with your desired URL

        // Clear the form
        document.getElementById('appointmentForm').reset();
    });
});
