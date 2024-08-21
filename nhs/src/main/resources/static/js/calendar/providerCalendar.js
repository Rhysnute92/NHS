document.addEventListener('DOMContentLoaded', function () {
    var calendarEl = document.getElementById('calendar');

    var calendar = new FullCalendar.Calendar(calendarEl, {
        initialView: 'dayGridMonth',
        headerToolbar: {
            left: 'prev,next today',
            center: 'title',
            right: 'dayGridMonth,timeGridWeek,timeGridDay'
        },
        events: [
            /* Thymeleaf variables converted to JavaScript objects */
            /* Convert Thymeleaf appointments to JavaScript format */
            /* Example of dynamically generated events from Thymeleaf */
            /* Note: Ensure appointments data is serialized to JSON correctly */
            [[${appointments}]].map(function(appointment) {
                return {
                    title: appointment.type,
                    start: appointment.date + 'T' + appointment.time,
                    /* Optional end time and other properties */
                };
            })
        ]
    });

    calendar.render();
});

function showList() {
    document.getElementById('appointment-list1').style.display = 'block'; // Show the list
    document.getElementById('calendar1').style.display = 'none'; // Hide the calendar
    document.getElementById('no-appointments').style.display = 'none'; // Hide "No appointments" message
}

function showProviderCalendar() {
    document.getElementById('appointment-list1').style.display = 'none'; // Hide the list
    document.getElementById('calendar1').style.display = 'block'; // Show the calendar
    document.getElementById('no-appointments').style.display = 'none'; // Hide "No appointments" message
}

// Initial setup to display the correct tab based on available data
window.onload = function() {
    const appointmentsExist = document.querySelector('#appointment-list tbody tr');
    if (appointmentsExist) {
        showList(); // Show list by default if appointments exist
    } else {
        document.getElementById('appointment-list1').style.display = 'none';
        document.getElementById('calendar1').style.display = 'none';
        document.getElementById('no-appointments').style.display = 'block';
    }
}