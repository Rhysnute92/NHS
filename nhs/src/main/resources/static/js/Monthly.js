let appointments = [];

let appointmentDateInput = document.getElementById("appointmentDate");
let appointmentTimeInput = document.getElementById("appointmentTime");
let appointmentAppointmentTypeInput = document.getElementById("appointmentType");
let appointmentDrInput = document.getElementById("dr");
let appointmentCommentInput = document.getElementById("comments");
let apptList = document.getElementById("apptList");

let appointmentIdCounter = 1;

function addAppointment() {
    let date = appointmentDateInput.value;
    let time = appointmentTimeInput.value;
    let appointmentType = appointmentAppointmentTypeInput.value;
    let dr = appointmentDrInput.value;
    let comment = appointmentCommentInput.value;

    if (date && time && appointmentType && dr) {
        let appointmentId = appointmentIdCounter++;

        appointments.push({
            id: appointmentId,
            date: date,
            time: time,
            appointmentType: appointmentType,
            dr: dr,
            comment: comment
        });

        showCalendar(currentMonth, currentYear);
        appointmentDateInput.value = "";
        appointmentTimeInput.value = "";
        appointmentAppointmentTypeInput.value = "";
        appointmentDrInput.value = "";
        appointmentCommentInput.value = "";
        apptSchedule();
    }
}

function deleteAppointment(appointmentId) {
    let appointmentIndex = appointments.findIndex(appointment => appointment.id === appointmentId);

    if (appointmentIndex !== -1) {
        appointments.splice(appointmentIndex, 1);
        showCalendar(currentMonth, currentYear);
        apptSchedule();
    }
}

function apptSchedule() {
    apptList.innerHTML = "";
    for (let i = 0; i < appointments.length; i++) {
        let appointment = appointments[i];
        let appointmentDate = new Date(appointment.date);
        if (appointmentDate.getMonth() === currentMonth && appointmentDate.getFullYear() === currentYear) {
            let listItem = document.createElement("li");
            listItem.innerHTML = `<strong>${appointment.appointmentType}</strong> with Dr. ${appointment.dr} on ${appointmentDate.toLocaleDateString()} at ${appointment.time}`;

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

function generate_year_range(start, end) {
    let years = "";
    for (let year = start; year <= end; year++) {
        years += `<option value='${year}'>${year}</option>`;
    }
    return years;
}

let today = new Date();
let currentMonth = today.getMonth();
let currentYear = today.getFullYear();
let selectYear = document.getElementById("year");
let selectMonth = document.getElementById("month");

let createYear = generate_year_range(1970, 2050);
selectYear.innerHTML = createYear;

let calendar = document.getElementById("calendar");

let months = [
    "January", "February", "March", "April", "May", "June",
    "July", "August", "September", "October", "November", "December"
];
let days = ["Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"];

let dataHead = "<tr>";
for (let dhead in days) {
    dataHead += `<th data-days='${days[dhead]}'>${days[dhead]}</th>`;
}
dataHead += "</tr>";

document.getElementById("thead-month").innerHTML = dataHead;

let monthAndYear = document.getElementById("monthAndYear");

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
    let tbl = document.getElementById("calendar-body");
    tbl.innerHTML = "";
    monthAndYear.innerHTML = `${months[month]} ${year}`;
    selectYear.value = year;
    selectMonth.value = month;

    let date = 1;
    for (let i = 0; i < 6; i++) {
        let row = document.createElement("tr");
        for (let j = 0; j < 7; j++) {
            if (i === 0 && j < firstDay) {
                let cell = document.createElement("td");
                let cellText = document.createTextNode("");
                cell.appendChild(cellText);
                row.appendChild(cell);
            } else if (date > daysInMonth(month, year)) {
                break;
            } else {
                let cell = document.createElement("td");
                cell.setAttribute("data-date", date);
                cell.setAttribute("data-month", month + 1);
                cell.setAttribute("data-year", year);
                cell.setAttribute("data-month_name", months[month]);
                cell.className = "date-picker";
                cell.innerHTML = `<span>${date}</span>`;

                if (
                    date === today.getDate() &&
                    year === today.getFullYear() &&
                    month === today.getMonth()
                ) {
                    cell.className = "date-picker selected";
                }

                if (hasAppointmentOnDate(date, month, year)) {
                    cell.classList.add("appointment-marker");
                    cell.appendChild(createAppointmentTooltip(date, month, year));
                }

                row.appendChild(cell);
                date++;
            }
        }
        tbl.appendChild(row);
    }
    apptSchedule();
}

function createAppointmentTooltip(date, month, year) {
    let tooltip = document.createElement("div");
    tooltip.className = "appointment-tooltip";
    let appointmentsOnDate = getAppointmentsOnDate(date, month, year);
    for (let i = 0; i < appointmentsOnDate.length; i++) {
        let appointment = appointmentsOnDate[i];
        let appointmentDate = new Date(appointment.date);
        let appointmentText = `<strong>${appointment.appointmentType}</strong> with Dr. ${appointment.dr} on ${appointmentDate.toLocaleDateString()} at ${appointment.time}`;
        let appointmentElement = document.createElement("p");
        appointmentElement.innerHTML = appointmentText;
        tooltip.appendChild(appointmentElement);
    }
    return tooltip;
}

function getAppointmentsOnDate(date, month, year) {
    return appointments.filter(function (appointment) {
        let appointmentDate = new Date(appointment.date);
        return (
            appointmentDate.getDate() === date &&
            appointmentDate.getMonth() === month &&
            appointmentDate.getFullYear() === year
        );
    });
}

function hasAppointmentOnDate(date, month, year) {
    return getAppointmentsOnDate(date, month, year).length > 0;
}

function daysInMonth(month, year) {
    return 32 - new Date(year, month, 32).getDate();
}

showCalendar(currentMonth, currentYear);
