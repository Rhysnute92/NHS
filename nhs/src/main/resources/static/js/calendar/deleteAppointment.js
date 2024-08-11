document.addEventListener('DOMContentLoaded', (event) => {
    // Get all appointment elements
    var appointments = document.querySelectorAll('.appointment');

    appointments.forEach(function(appointment) {
        var type = appointment.getAttribute('data-type');
        var deleteButton = appointment.querySelector('.delete-button');
        var specialMessage = appointment.querySelector('.special-message');

        if (type === 'lymphoedema') {
            // Option 1: Hide the delete button
            // deleteButton.style.display = 'none'; // Uncomment to hide the button

            // Option 2: Disable the delete button
            deleteButton.disabled = true; // Uncomment to disable the button
            deleteButton.title = "You cannot delete lymphoedema appointments."; // Optional tooltip

            // Additional Condition: Show a special message
            if (specialMessage) {
                specialMessage.style.display = 'block'; // Show special message
            }
        } else {
            // Attach delete functionality if not lymphoedema
            deleteButton.addEventListener('click', function() {
                var appointmentId = appointment.getAttribute('data-id');
                deleteAppointment(appointmentId);
            });
        }
    });
});

// Function to handle appointment deletion
function deleteAppointment(appointmentId) {
    if (confirm('Are you sure you want to delete this appointment?')) {
        fetch(`/appointments/${appointmentId}/delete`, {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json',
                'X-CSRFToken': getCsrfToken() // Get CSRF token if needed
            }
        })
            .then(response => {
                if (response.ok) {
                    alert('Appointment deleted successfully.');
                    location.reload(); // Reload the page or remove the element from the DOM
                } else {
                    alert('Failed to delete appointment.');
                }
            })
            .catch(error => {
                console.error('Error:', error);
                alert('An error occurred.');
            });
    }
}

// Function to get CSRF token (if using Django or similar)
function getCsrfToken() {
    var csrfToken = document.querySelector('[name=csrfmiddlewaretoken]').value;
    return csrfToken;
}
