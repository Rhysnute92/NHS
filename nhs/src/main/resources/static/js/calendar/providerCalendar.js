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
    document.getElementById('appointment-list').classList.remove('inactive');
    document.getElementById('calendar').classList.remove('active');
    document.getElementById('calendar').classList.add('inactive');
}

function showProviderCalendar() {
    document.getElementById('calendar').classList.add('active');
    document.getElementById('calendar').classList.remove('inactive');
    document.getElementById('appointment-list').classList.remove('active');
    document.getElementById('appointment-list').classList.add('inactive');
}

const patient = "Lord John Doe"; //testing purposes only
document.querySelector(".management-desktop-title").innerHTML = '${patient} Calendar';