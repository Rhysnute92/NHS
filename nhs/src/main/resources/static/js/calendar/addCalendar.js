document.getElementById('appointmentForm').addEventListener('submit', function(event) {
    event.preventDefault();

    // Extract data from form
    var title = document.getElementById('title').value;
    var start = document.getElementById('start').value;
    var end = document.getElementById('end').value;

    // Validate the form data if necessary
    if (!title || !start || !end) {
        alert('All fields are required.');
        return;
    }

    // Add the appointment to the calendar
    var calendar = document.getElementById('calendar-body').fullCalendar; // Adjust to your calendar instance
    calendar.addEvent({
        title: title,
        start: start,
        end: end
    });
    // Hide the modal
    document.getElementById('myModal').style.display = 'none';

    // Optionally clear the form
    document.getElementById('appointmentForm').reset();
});