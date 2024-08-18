function openAppointmentForm() {
    document.getElementById('appointment-form').style.display = 'block';
}

function closeAppointmentForm() {
    document.getElementById('appointment-form').style.display = 'none';
}

function saveAppointment() {
    const date = document.getElementById('appointment-date').value;
    const time = document.getElementById('appointment-time').value;
    const description = document.getElementById('appointment-description').value;

    fetch('/api/appointments', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            date: date,
            time: time,
            description: description
        })
    })
        .then(response => response.json())
        .then(data => {
            // Refresh the appointment list or handle successful save
            closeAppointmentForm();
            location.reload(); // Reload to see the updated list (alternative approach: update list dynamically)
        })
        .catch(error => {
            alert('Error saving appointment.');
        });
}
