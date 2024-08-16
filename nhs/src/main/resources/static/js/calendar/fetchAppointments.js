async function fetchAppointments(calendarId) {
    try {
        const response = await fetch(`/appointments/calendar/${calendarId}`);
        if (!response.ok) {
            throw new Error('Network response was not ok');
        }
        const appointments = await response.json();
        console.log(appointments);
        // Do something with the appointments
    } catch (error) {
        console.error('There was a problem with the fetch operation:', error);
    }
}