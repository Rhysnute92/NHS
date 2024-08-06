// Initialize events array and event ID counter
let events = [], eventIdCounter = 1;

// Get current date, month, and year
let currentDate = new Date();
let currentMonth = currentDate.getMonth(), currentYear = currentDate.getFullYear();

// Arrays for days of the week and months of the year
const daysOfWeek = ["Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"];
const months = ["January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"];

// Populate year dropdown with a range of years and set the days of the week header
document.getElementById("year").innerHTML = generateYearRange(1970, 2050);
document.getElementById("thead-month").innerHTML = daysOfWeek.map(day => `<th>${day}</th>`).join("");

// Add event listeners for navigation and adding events
document.getElementById("addEventBtn").addEventListener("click", addEvent);
document.getElementById("prev").addEventListener("click", previous);
document.getElementById("next").addEventListener("click", next);
document.getElementById("jump").addEventListener("change", jump);
document.getElementById("saveAppointmentBtn").addEventListener("click", saveAppointment);
document.querySelector(".close").addEventListener("click", closeModal);

// Show the initial calendar for the current month and year
window.onload = function() {
    document.getElementById("year").value = currentYear; // Set the dropdown to the current year
    document.getElementById("month").value = currentMonth; // Set the dropdown to the current month
    showCalendar(currentMonth, currentYear);
    displayAppointments(); // Display appointments on load
};

// Generate a range of years for the year dropdown
function generateYearRange(start, end) {
    return Array.from({ length: end - start + 1 }, (_, i) => `<option value="${start + i}">${start + i}</option>`).join("");
}

// Show the modal to add an appointment
function openModal(date) {
    document.getElementById("appointmentDate").value = date.toISOString().split('T')[0]; // Set the date in the form
    document.getElementById("appointmentModal").style.display = "block"; // Show the modal
}

// Close the modal
function closeModal() {
    document.getElementById("appointmentModal").style.display = "none"; // Hide the modal
}

// Add a new event to the calendar
function saveAppointment() {
    let eventDetails = {
        id: eventIdCounter++, // Generate a unique ID for the event
        date: document.getElementById("appointmentDate").value,
        time: document.getElementById("appointmentTime").value,
        appointmentType: document.getElementById("appointmentType").value,
        dr: document.getElementById("dr").value,
        description: document.getElementById("comments").value,
    };
    if (eventDetails.date) { // Ensure the event has a date before adding
        events.push(eventDetails); // Add the event to the events array
        closeModal(); // Close the modal
        resetForm(); // Reset the form inputs
        updateCalendar(); // Update the calendar and display the event
    }
}

// Delete an event by ID
function deleteAppointment(eventId) {
    events = events.filter(event => event.id !== eventId); // Remove the event from the array
    updateCalendar(); // Update the calendar display
}

// Update the calendar and appointments list
function updateCalendar() {
    showCalendar(currentMonth, currentYear); // Refresh the calendar
    displayAppointments(); // Refresh the appointments list
}

// Display the list of appointments for the current month and year
function displayAppointments() {
    let apptList = document.getElementById("apptList");
    let deleteBtn = document.getElementById("deleteBtn"); // Assuming you have a delete button with this ID

    // Check if there are appointments for the current month
    let hasAppointments = events.some(event => new Date(event.date).getMonth() === currentMonth && new Date(event.date).getFullYear() === currentYear);

    // Toggle the delete button visibility based on whether there are appointments
    deleteBtn.classList.toggle("hidden", !hasAppointments);

    // Populate the appointments list
    apptList.innerHTML = events
        .filter(event => new Date(event.date).getMonth() === currentMonth && new Date(event.date).getFullYear() === currentYear)
        .map(event => `
            <li data-event-id="${event.id}">
                <strong>${event.appointmentType}</strong> - ${event.description} on ${new Date(event.date).toLocaleDateString()}
                <button class="delete-appt" onclick="deleteAppointment(${event.id})">Delete</button>
            </li>`).join("");
}

// Show the calendar for a specific month and year
function showCalendar(month, year) {
    let tbl = document.getElementById("calendar-body");
    tbl.innerHTML = ""; // Clear the current calendar
    document.getElementById("monthAndYear").textContent = `${months[month]} ${year}`;
    document.getElementById("year").value = year;
    document.getElementById("month").value = month;

    let firstDay = new Date(year, month, 1).getDay(); // Get the first day of the month
    let date = 1, daysInMonth = 32 - new Date(year, month, 32).getDate(); // Calculate the number of days in the month

    // Create calendar rows and cells
    for (let i = 0; i < 6; i++) { // Loop through weeks
        let row = document.createElement("tr");
        for (let j = 0; j < 7; j++) { // Loop through days
            let cell = document.createElement("td");
            if (i === 0 && j < firstDay) {
                cell.textContent = ""; // Empty cells before the first day
            } else if (date > daysInMonth) {
                break;
            } else {
                let cellDate = new Date(year, month, date);
                cell.innerHTML = `<span>${date}</span>`;
                cell.className = "date-picker";
                if (cellDate.toDateString() === currentDate.toDateString()) cell.classList.add("selected"); // Highlight current date

                if (hasEventOnDate(date, month, year)) { // Check for events on this date
                    cell.classList.add("event-marker");
                    cell.appendChild(createEventTooltip(date, month, year)); // Add event tooltip
                }

                // Add click event to open the modal on date click
                cell.addEventListener("click", function() {
                    openModal(cellDate); // Open modal with the clicked date
                });

                date++;
            }
            row.appendChild(cell);
        }
        tbl.appendChild(row);
    }
}

// Check if there are events on a specific date
function hasEventOnDate(date, month, year) {
    return events.some(event => {
        let eventDate = new Date(event.date);
        return eventDate.getDate() === date && eventDate.getMonth() === month && eventDate.getFullYear() === year;
    });
}

// Create a tooltip to display event details
function createEventTooltip(date, month, year) {
    let tooltip = document.createElement("div");
    tooltip.className = "event-tooltip";
    let eventsOnDate = events.filter(event => {
        let eventDate = new Date(event.date);
        return eventDate.getDate() === date && eventDate.getMonth() === month && eventDate.getFullYear() === year;
    });
    eventsOnDate.forEach(event => {
        let eventText = `<strong>${event.appointmentType}</strong> - ${event.description} on ${new Date(event.date).toLocaleDateString()}`;
        let eventElement = document.createElement("p");
        eventElement.innerHTML = eventText;
        tooltip.appendChild(eventElement);
    });
    return tooltip;
}

// Reset the event form inputs
function resetForm() {
    document.getElementById("appointmentDate").value = "";
    document.getElementById("appointmentTime").value = "";
    document.getElementById("appointmentType").value = "";
    document.getElementById("dr").value = "";
    document.getElementById("comments").value = "";
}

// Navigate to the next month
function next() {
    currentMonth = (currentMonth + 1) % 12;
    if (currentMonth === 0) currentYear++;
    updateCalendar();
}

// Navigate to the previous month
function previous() {
    currentMonth = (currentMonth - 1 + 12) % 12;
    if (currentMonth === 11) currentYear--;
    updateCalendar();
}

// Jump to a specific month and year based on user input
function jump() {
    currentYear = parseInt(document.getElementById("year").value);
    currentMonth = parseInt(document.getElementById("month").value);
    updateCalendar();
}
