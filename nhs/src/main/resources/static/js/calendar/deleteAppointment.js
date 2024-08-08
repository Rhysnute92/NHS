// Function to update the visibility of the delete button based on appointments
function updateDeleteButtonVisibility() {
    const deleteButton = document.getElementById('deleteButton');
    if (appointments.length === 0) {
        deleteButton.style.display = 'none';
    } else {
        deleteButton.style.display = 'inline-block'; // Or 'block' depending on your layout
    }
}

// Function to delete an appointment by ID
function deleteAppointment(appointmentId) {
    // Find the appointment by ID
    const appointmentIndex = appointments.findIndex(appt => appt.id === appointmentId);

    if (appointmentIndex !== -1) {
        const appointment = appointments[appointmentIndex];

        // Check if the appointment is of type 'lymphoedema'
        if (appointment.type === 'lymphoedema') {
            alert('This appointment cannot be deleted.');
            return;
        }

        // Ask for confirmation
        const confirmDeletion = confirm(`Are you sure you want to delete the appointment: "${appointment.title}"?`);

        if (confirmDeletion) {
            // Delete the appointment
            appointments.splice(appointmentIndex, 1);
            alert('Appointment successfully deleted.');
            updateDeleteButtonVisibility(); // Update visibility after deletion
        }
    } else {
        alert('Appointment not found.');
    }
}

// Example usage
document.getElementById('delete-event').addEventListener('click', function() {
    const appointmentId = parseInt(prompt('Enter the appointment ID to delete:'), 10);
    deleteAppointment(appointmentId);
});

// Initial check for delete button visibility
updateDeleteButtonVisibility();
