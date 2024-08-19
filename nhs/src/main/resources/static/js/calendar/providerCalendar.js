const appointments = /*[[${appointments}]]*/ [];

// Initialize FullCalendar
document.addEventListener('DOMContentLoaded', function() {
    const calendarEl = document.getElementById('calendar');
    const calendar = new FullCalendar.Calendar(calendarEl, {
        initialView: 'dayGridMonth',
        events: appointments.map(appointment => ({
            title: appointment.type,
            start: `${appointment.date}T${appointment.time}`,
            description: appointment.location,
        })),
        eventClick: function(info) {
            alert(`Appointment Type: ${info.event.title}\nLocation: ${info.event.extendedProps.description}`);
        }
    });
    calendar.render();
});

// Toggle views
function showList() {
    document.getElementById('appointment-list').classList.add('active');
    document.getElementById('calendar').classList.remove('active');
}

function showCalendar() {
    document.getElementById('calendar').classList.add('active');
    document.getElementById('appointment-list').classList.remove('active');
}