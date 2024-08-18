let appointments = [];

const apptDateInput = document.getElementById("apptDate");
const apptTimeInput = document.getElementById("apptTime");
const apptTypeInput = document.getElementById("apptType");
const apptProviderInput = document.getElementById("apptProvider");
const apptInfoInput = document.getElementById("apptInfo");
const apptList = document.getElementById("apptList");

const apptForm = document.getElementById("apptForm");

if (apptForm) {
    apptForm.addEventListener("submit", function (evt) {
        evt.preventDefault();
        addAppointment();
    });
}

async function addAppointment() {
    const appointment = {
        date: apptDateInput.value,
        time: apptTimeInput.value,
        appointmentType: apptTypeInput.value,
        provider: apptProviderInput.value,
        description: apptInfoInput.value
    };

    const url = "/appointments";
    try {
        const response = await fetch(url, {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(appointment)
        });
        if (response.status === 201) {
            if (window.location.href.includes("appt")) {
                window.location.href = "/calendar";
            }
            const newAppointment = await response.json();
            appointments.push(newAppointment);
            clearForm();
            showCalendar(currentMonth, currentYear);
            displayAppointments();
        }
    } catch (error) {
        console.error("Error:", error);
    }
}

function clearForm() {
    apptDateInput.value = "";
    apptTimeInput.value = "";
    apptTypeInput.value = "";
    apptProviderInput.value = "";
    apptInfoInput.value = "";
}

function deleteAppointment(appointmentID) {
    let appointmentIndex = appointments.findIndex((appointment) => appointment.id === appointmentID);

    if (appointmentIndex !== -1) {
        appointments.splice(appointmentIndex, 1);

        showCalendar(currentMonth, currentYear);
        displayAppointments();
    }
}

function displayAppointments() {
    apptList.innerHTML = "";
    for (let appointment of appointments) {
        let apptDate = new Date(appointment.apptDateTime);
        if (apptDate.getMonth() === currentMonth && apptDate.getFullYear() === currentYear) {
            let listItem = document.createElement("li");
            listItem.innerHTML = `<strong>${appointment.apptType}</strong> - ${appointment.apptInfo} on ${apptDate.toLocaleDateString()} at ${apptDate.toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' })}`;
            listItem.className = "appt-item";

            let deleteButton = document.createElement("button");
            deleteButton.className = "delete-appt";
            deleteButton.textContent = "Delete";
            deleteButton.onclick = function () {
                deleteAppointment(appointment.id);
            };

            listItem.appendChild(deleteButton);
            apptList.appendChild(listItem);
        }
    }
}

function generateYearRange(start, end) {
    let years = "";
    for (let year = start; year <= end; year++) {
        years += `<option value='${year}'>${year}</option>`;
    }
    return years;
}

let today = new Date();
let currentMonth = today.getMonth();
let currentYear = today.getFullYear();
const selectYear = document.getElementById("year");
const selectMonth = document.getElementById("month");

const createYear = generateYearRange(1970, 2050);
document.getElementById("year").innerHTML = createYear;

const calendar = document.getElementById("calendar");

const months = ["January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"];
const days = ["Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"];

let dataHead = "<tr>";
for (let day of days) {
    dataHead += `<th data-days='${day}'>${day}</th>`;
}
dataHead += "</tr>";
document.getElementById("thead-month").innerHTML = dataHead;

const monthAndYear = document.getElementById("monthAndYear");

document.addEventListener("DOMContentLoaded", function () {
    showCalendar(currentMonth, currentYear);
    fetchAppointments();
});

async function fetchAppointments() {
    try {
        const response = await fetch('/appointments');
        if (response.ok) {
            appointments = await response.json();
            showCalendar(currentMonth, currentYear);
            displayAppointments();
        } else {
            console.error("Failed to fetch appointments");
        }
    } catch (error) {
        console.error("Error:", error);
    }
}

function next() {
    currentYear = currentMonth === 11 ? currentYear + 1 : currentYear;
    currentMonth = (currentMonth + 1) % 12;
    showCalendar(currentMonth, currentYear);
}

function previous() {
    currentYear = currentMonth === 0 ? currentYear - 1 : currentYear;
    currentMonth = currentMonth === 0 ? 11 : currentMonth - 1;
    showCalendar(currentMonth, currentYear);
}

function jump() {
    currentYear = parseInt(selectYear.value);
    currentMonth = parseInt(selectMonth.value);
    showCalendar(currentMonth, currentYear);
}

function showCalendar(month, year) {
    let firstDay = new Date(year, month, 1).getDay();
    const tbl = document.getElementById("calendar-body");
    tbl.innerHTML = "";
    monthAndYear.innerHTML = `${months[month]} ${year}`;
    selectYear.value = year;
    selectMonth.value = month;

    let date = 1;
    const fragment = document.createDocumentFragment();

    for (let i = 0; i < 6; i++) {
        let row = document.createElement("tr");
        for (let j = 0; j < 7; j++) {
            let cell = document.createElement("td");

            if (i === 0 && j < firstDay) {
                cell.appendChild(document.createTextNode(""));
            } else if (date > daysInMonth(month, year)) {
                break;
            } else {
                cell.setAttribute("data-date", date);
                cell.setAttribute("data-month", month + 1);
                cell.setAttribute("data-year", year);
                cell.setAttribute("data-month_name", months[month]);
                cell.className = "date-picker";
                cell.innerHTML = `<span>${date}</span>`;

                if (date === today.getDate() && year === today.getFullYear() && month === today.getMonth()) {
                    cell.className = "date-picker selected";
                }

                if (hasAppointmentOnDate(date, month, year)) {
                    cell.classList.add("appt-marker");
                    cell.appendChild(createAppointmentTooltips(date, month, year));
                }

                date++;
            }
            row.appendChild(cell);
        }
        fragment.appendChild(row);
    }
    tbl.appendChild(fragment);
}

function createAppointmentTooltips(date, month, year) {
    const tooltips = document.createElement('div');
    const appointmentsOnDate = getAppointmentsOnDate(date, month, year);
    for (let appointment of appointmentsOnDate) {
        const tooltip = document.createElement('div');
        tooltip.className = 'appt-tooltip';
        tooltip.innerHTML = `${appointment.apptType}`;
        tooltips.appendChild(tooltip);
    }
    return tooltips;
}

function getAppointmentsOnDate(date, month, year) {
    return appointments.filter(appointment => {
        let apptDate = new Date(appointment.apptDateTime);
        return apptDate.getDate() === date && apptDate.getMonth() === month && apptDate.getFullYear() === year;
    });
}

function hasAppointmentOnDate(date, month, year) {
    return getAppointmentsOnDate(date, month, year).length > 0;
}

function daysInMonth(month, year) {
    return 32 - new Date(year, month, 32).getDate();
}
