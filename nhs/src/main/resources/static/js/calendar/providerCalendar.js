// Function to show the appointment list
function showList() {
    document.getElementById('appointment-list').style.display = 'block'; // Show the list
    document.getElementById('calendar').style.display = 'none'; // Hide the calendar
    document.getElementById('no-appointments').style.display = 'none'; // Hide "No appointments" message
}

// Function to show the calendar
function showProviderCalendar() {
    document.getElementById('appointment-list').style.display = 'none'; // Hide the list
    document.getElementById('calendar').style.display = 'block'; // Show the calendar
    document.getElementById('no-appointments').style.display = 'none'; // Hide "No appointments" message
}

// Initial setup to display the correct tab based on available data
window.onload = function() {
    // Check if there are appointments
    const appointmentsExist = document.querySelector('#appointment-list tbody tr');
    if (appointmentsExist) {
        showList(); // Show list by default if appointments exist
    } else {
        // Show "No appointments" message if there are no appointments
        document.getElementById('appointment-list').style.display = 'none';
        document.getElementById('calendar').style.display = 'none';
        document.getElementById('no-appointments').style.display = 'block';
    }
}
document.addEventListener('DOMContentLoaded', function () {
    var calendarEl = document.getElementById('calendar');

    var calendar = new FullCalendar.Calendar(calendarEl, {
        initialView: 'dayGridMonth', // Sets the initial view to month
        headerToolbar: {
            left: 'prev,next today',
            center: 'title',
            right: 'dayGridMonth,timeGridWeek,timeGridDay'
        },
        events: [
            /* Example of hardcoded events, replace with dynamic data if necessary */
            {
                title: 'Appointment with Dr. Smith',
                start: '2024-08-25T10:30:00',
                end: '2024-08-25T11:30:00'
            },
            {
                title: 'Therapy Session',
                start: '2024-08-28T12:00:00',
                end: '2024-08-28T13:00:00'
            }
            // More events can be added here
        ]
    });

    calendar.render();
});

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
            /* Dynamic events populated by Thymeleaf */
            /* Assuming each appointment has a date, startTime, endTime, and title */
            /* In Thymeleaf, we loop through and create event objects */
            /* Example Thymeleaf syntax to generate JavaScript objects */
            /* Note: make sure your date formats are compatible with JavaScript Date objects */
            /* Example below assumes appointments is a list of objects in your model */

            /*
              In your model:
              - Each event could be structured as {date: '2024-08-25', startTime: '10:30:00', endTime: '11:30:00', title: 'Appointment with Dr. Smith'}
            */

            /* Thymeleaf will loop through each appointment and generate the correct JavaScript code */

            /* Example (inside Thymeleaf template): */

            /*
            <tr th:each="appointment : ${appointments}">
                {
                    title: '[[${appointment.title}]]',
                    start: '[[${appointment.date}T${appointment.startTime}]]',
                    end: '[[${appointment.date}T${appointment.endTime}]]'
                },
            </tr>
            */
        ]
    });

    calendar.render();
});


