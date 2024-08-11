// Example array of appointments
const appointments = [
    { id: 1, type: 'consultation', date: '2024-08-15' },
    { id: 2, type: 'lymphoedema', date: '2024-08-16' },
    { id: 3, type: 'therapy', date: '2024-08-17' },
];

// Function to delete an appointment by ID
function deleteAppointment(appointmentId) {
    // Find the appointment by ID
    const appointment = appointments.find(appt => appt.id === appointmentId);

    if (!appointment) {
        console.log("Appointment not found.");
        return;
    }

    // Check if the appointment is related to lymphoedema
    if (appointment.type === 'lymphoedema') {
        console.log("Lymphoedema appointments cannot be deleted.");
        return;
    }

    // Proceed with deletion if it's not lymphoedema
    const index = appointments.indexOf(appointment);
    if (index > -1) {
        appointments.splice(index, 1);
        console.log(`Appointment with ID ${appointmentId} deleted successfully.`);
    }
}

// Test the function
deleteAppointment(2); // Should prevent deletion
deleteAppointment(1); // Should allow deletion
console.log(appointments); // Check the updated list of appointments
