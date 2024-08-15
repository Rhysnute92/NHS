// calendar.js

// Initialize variables for the current date, month, and year
let today = new Date();
let currentMonth = today.getMonth();
let currentYear = today.getFullYear();

// Get references to the year and month dropdowns
const selectYear = document.getElementById("year");
const selectMonth = document.getElementById("month");

// Array of month names and day names
const months = ["January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"];
const days = ["Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"];

// Get references to the calendar and the month/year display
const monthAndYear = document.getElementById("monthAndYear");
const calendar = document.getElementById("calendar");

// Generate a range of years for the year dropdown
function generateYearRange(start, end) {
    let years = "";
    for (let year = start; year <= end; year++) {
        years += `<option value='${year}'>${year}</option>`;
    }
    return years;
}

// Populate the year dropdown with the generated years
const createYear = generateYearRange(1970, 2050);
document.getElementById("year").innerHTML = createYear;

// Create the table header with day names
const dataHead = "<tr>" + days.map(day => `<th data-days='${day}'>${day}</th>`).join('') + "</tr>";
document.getElementById("thead-month").innerHTML = dataHead;

// Function to move to the next month
function next() {
    currentYear = currentMonth === 11 ? currentYear + 1 : currentYear; // If it's December, increment the year
    currentMonth = (currentMonth + 1) % 12; // Move to the next month
    showCalendar(currentMonth, currentYear); // Update the calendar display
}

// Function to move to the previous month
function previous() {
    currentYear = currentMonth === 0 ? currentYear - 1 : currentYear; // If it's January, decrement the year
    currentMonth = currentMonth === 0 ? 11 : currentMonth - 1; // Move to the previous month
    showCalendar(currentMonth, currentYear); // Update the calendar display
}

// Function to jump to a selected month and year
function jump() {
    currentYear = parseInt(selectYear.value); // Get the selected year
    currentMonth = parseInt(selectMonth.value); // Get the selected month
    showCalendar(currentMonth, currentYear); // Update the calendar display
}

// Function to display the calendar for a specific month and year
function showCalendar(month, year) {
    let firstDay = new Date(year, month, 1).getDay(); // Get the first day of the month
    const tbl = document.getElementById("calendar-body"); // Get the calendar body
    tbl.innerHTML = ""; // Clear the calendar body
    monthAndYear.innerHTML = `${months[month]} ${year}`; // Update the month and year display
    selectYear.value = year; // Update the year dropdown
    selectMonth.value = month; // Update the month dropdown

    let date = 1; // Initialize the date
    const fragment = document.createDocumentFragment(); // Create a document fragment to hold the rows

    for (let i = 0; i < 6; i++) { // Create 6 rows (weeks)
        let row = document.createElement("tr");
        for (let j = 0; j < 7; j++) { // Create 7 cells (days) for each row
            let cell = document.createElement("td");

            if (i === 0 && j < firstDay) { // If this cell is before the first day of the month
                cell.appendChild(document.createTextNode("")); // Leave it empty
            } else if (date > daysInMonth(month, year)) { // If we've reached the end of the month
                break; // Stop creating cells
            } else {
                cell.setAttribute("data-date", date); // Set attributes for the cell
                cell.setAttribute("data-month", month + 1);
                cell.setAttribute("data-year", year);
                cell.setAttribute("data-month_name", months[month]);
                cell.className = "date-picker"; // Add a class for styling
                cell.innerHTML = `<span>${date}</span>`; // Set the date

                // Highlight the current date
                if (date === today.getDate() && year === today.getFullYear() && month === today.getMonth()) {
                    cell.className = "date-picker selected";
                }

                // Add a marker if there are appointments on this date
                if (hasAppointmentOnDate(date, month, year)) {
                    cell.classList.add("appt-marker");
                    cell.appendChild(createAppointmentTooltips(date, month, year));
                }

                date++; // Increment the date
            }
            row.appendChild(cell); // Add the cell to the row
        }
        fragment.appendChild(row); // Add the row to the fragment
    }
    tbl.appendChild(fragment); // Add the fragment to the calendar body
}

// Function to create tooltips for appointments on a specific date
function createAppointmentTooltips(date, month, year) {
    const tooltips = document.createElement('div'); // Create a div to hold the tooltips
    const appointmentsOnDate = getAppointmentsOnDate(date, month, year); // Get the appointments on this date
    for (let appointment of appointmentsOnDate) {
        const tooltip = document.createElement('div'); // Create a div for each tooltip
        tooltip.className = 'appt-tooltip'; // Add a class for styling
        tooltip.innerHTML = `${appointment.apptType}`; // Set the appointment type as the tooltip content
        tooltips.appendChild(tooltip); // Add the tooltip to the tooltips div
    }
    return tooltips; // Return the tooltips div
}

// Function to get appointments on a specific date
function getAppointmentsOnDate(date, month, year) {
    return appointments.filter(appointment => {
        let apptDate = new Date(appointment.apptDateTime); // Convert the appointment date to a Date object
        return apptDate.getDate() === date && apptDate.getMonth() === month && apptDate.getFullYear() === year; // Check if the date matches
    });
}

// Function to check if there are appointments on a specific date
function hasAppointmentOnDate(date, month, year) {
    return getAppointmentsOnDate(date, month, year).length > 0; // Return true if there are appointments, otherwise false
}

// Function to get the number of days in a month
function daysInMonth(month, year) {
    return 32 - new Date(year, month, 32).getDate(); // Return the number of days in the month
}

// Show the calendar when the DOM content is loaded
document.addEventListener("DOMContentLoaded", function () {
    showCalendar(currentMonth, currentYear);
});

