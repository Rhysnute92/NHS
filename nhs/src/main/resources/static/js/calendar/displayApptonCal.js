let appointments = [];

// Function to render the calendar and display appointments
function renderCalendar() {
    const calendar = document.getElementById('calendar');
    calendar.innerHTML = ''; // Clear previous calendar content

    appointments.forEach(appt => {
        const appointmentElement = document.createElement('div');
        appointmentElement.textContent = `${appt.date}: ${appt.title} (${appt.type})`;
        calendar.appendChild(appointmentElement);
    });
}

// Function to add a new appointment
function addAppointment() {
    const date = document.getElementById('appointmentDate').value;

    if (date) {
        const newAppointment = {
            id: appointments.length + 1, // Simple ID assignment
            date
        };

        appointments.push(newAppointment);
        renderCalendar();
    } else {
        alert('Please enter a date for your appointment.');
    }
}

// Event listener for adding appointments
document.getElementById('addAppointmentButton').addEventListener('click', addAppointment);

// Initial rendering
renderCalendar();
