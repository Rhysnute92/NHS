// Current month and year for the calendar
let currentMonth;
let currentYear;

// DOM elements for year and month selectors
const selectYear = document.getElementById("year");
const selectMonth = document.getElementById("month");

// Month and day names for calendar
const months = ["January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"];
const days = ["Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"];

// Create table header for days of the week
let dataHead = "<tr>";
for (let day of days) {
    dataHead += `<th data-days='${day}'>${day}</th>`;
}
dataHead += "</tr>";

const monthAndYear = document.getElementById("monthAndYear");

// Initialize calendar on page load
document.addEventListener("DOMContentLoaded", function () {
    let today = new Date();
    currentMonth = today.getMonth();
    currentYear = today.getFullYear();

    showCalendar(currentMonth, currentYear);
    fetchAppointments(); // Fetch and display appointments
});

// Function to fetch appointments from the server
async function fetchAppointments() {
    try {
        const response = await fetch('/appointments');
        if (response.ok) {
            appointments = await response.json();
            showCalendar(currentMonth, currentYear);
            for (let appointment of appointments) {
                displayAppointment(appointment);
            }
        } else {
            console.error("Failed to fetch appointments");
        }
    } catch (error) {
        console.error("Error:", error);
    }
}

// Function to go to the next month
function next() {
    currentYear = currentMonth === 11 ? currentYear + 1 : currentYear;
    currentMonth = (currentMonth + 1) % 12;
    showCalendar(currentMonth, currentYear);
}

// Function to go to the previous month
function previous() {
    currentYear = currentMonth === 0 ? currentYear - 1 : currentYear;
    currentMonth = currentMonth === 0 ? 11 : currentMonth - 1;
    showCalendar(currentMonth, currentYear);
}

// Function to jump to a specific month and year
function jump() {
    currentYear = parseInt(selectYear.value);
    currentMonth = parseInt(selectMonth.value);
    showCalendar(currentMonth, currentYear);
}

// Function to create days of the week headers
function createDaysOfWeekHeader() {
    const daysHeader = document.getElementById("days-header");
    daysHeader.innerHTML = "";

    for (let day of days) {
        const dayDiv = document.createElement("div");
        dayDiv.textContent = day;
        daysHeader.appendChild(dayDiv);
    }
}

// Function to display the calendar for the current month and year
function showCalendar(month, year) {
    let firstDay = new Date(year, month, 1).getDay();
    const calendarGrid = document.getElementById("calendar-grid");
    calendarGrid.innerHTML = "";
    monthAndYear.innerHTML = `${months[month]} ${year}`;
    selectYear.value = year;
    selectMonth.value = month;

    createDaysOfWeekHeader();

    let date = 1;

    // Create empty cells for the days before the first day of the month
    for (let i = 0; i < firstDay; i++) {
        const emptyDiv = document.createElement("div");
        calendarGrid.appendChild(emptyDiv);
    }

    // Create cells for each date in the month
    for (let i = firstDay; date <= daysInMonth(month, year); i++) {
        const dateDiv = document.createElement("div");
        dateDiv.setAttribute("data-date", date);
        dateDiv.setAttribute("data-month", month + 1);
        dateDiv.setAttribute("data-year", year);
        dateDiv.setAttribute("data-month_name", months[month]);
        dateDiv.className = "date-picker";
        dateDiv.innerHTML = `<span>${date}</span>`;

        const today = new Date();
        // Highlight today's date
        if (date === today.getDate() && year === today.getFullYear() && month === today.getMonth()) {
            dateDiv.classList.add("selected");
        }

        // Add appointment markers if there are appointments on this date
        if (hasAppointmentOnDate(date, month, year)) {
            dateDiv.classList.add("appt-marker");
            dateDiv.appendChild(createAppointmentTooltips(date, month, year));
        }

        calendarGrid.appendChild(dateDiv);
        date++;
    }
}

// Function to create tooltips for appointments on a given date
function createAppointmentTooltips(date, month, year) {
    const tooltips = document.createElement('div');
    tooltips.className = 'appt-tooltips';
    const appointmentsOnDate = getAppointmentsOnDate(date, month, year);
    for (let appointment of appointmentsOnDate) {
        const tooltip = document.createElement('div');
        tooltip.className = 'appt-tooltip';
        tooltip.innerHTML = `${appointment.apptType}`;
        tooltips.appendChild(tooltip);
    }
    return tooltips;
}

// Function to get appointments on a specific date
function getAppointmentsOnDate(date, month, year) {
    return appointments.filter(appointment => {
        let apptDate = new Date(appointment.apptDateTime);
        return apptDate.getDate() === date && apptDate.getMonth() === month && apptDate.getFullYear() === year;
    });
}

// Function to check if there are appointments on a specific date
function hasAppointmentOnDate(date, month, year) {
    return getAppointmentsOnDate(date, month, year).length > 0;
}

// Function to get the number of days in a month
function daysInMonth(month, year) {
    return 32 - new Date(year, month, 32).getDate();
}