
let events = [];

let appointmentDateInput = document.getElementById("appointmentDate");
let appointmentTimeInput = document.getElementById("appointmentTime");
let appointmentTypeInput = document.getElementById("appointmentType");
let appointmentProviderInput = document.getElementById("dr");
let appointmentInfoInput = document.getElementById("comments");
let apptList = document.getElementById("apptList");

let eventIdCounter = 1;

function addAppointment() {
    let date = document.getElementById("appointmentDateInput").value;
    let time = document.getElementById("appointmentTimeInput").value;
    let appointmentType = document.getElementById("appointmentTypeInput").value;
    let dr = document.getElementById("appointmentProviderInput").value;
    let description = document.getElementById("appointmentInfoInput").value;

    if (date && title) {
        let appointmentId = appointmedIdCounter++;

        events.push(
            {
                id: appointmentId, date: date, time: time, appointmentType: appointmentType, dr: dr, description: description
            }
        );
        showCalendar(currentMonth, currentYear);
        appointmentDateInput.value = "";
        appointmentTimeInput.value = "";
        appointmentTypeInput.value = "";
        appointmentProviderInput.value = "";
        appointmentInfoInput.value = "";
        apptSchedule();
    }
}

function deleteAppointment(ApptID) {
    let eventIndex =
        events.findIndex((event) =>
            event.id === ApptID);

    if (eventIndex !== -1) {
        events.splice(eventIndex, 1);
        showCalendar(currentMonth, currentYear);
        displayReminders();
    }
}

function apptSchedule() {
    apptList.innerHTML = "";
    for (let i = 0; i < events.length; i++) {
        let event = events[i];
        let eventDate = new Date(event.date);
        if (eventDate.getMonth() ===
            currentMonth &&
            eventDate.getFullYear() ===
            currentYear) {
            let listItem = document.createElement("li");
            listItem.innerHTML =
                `<strong>${event.title}</strong> - 
			${event.description} on 
			${eventDate.toLocaleDateString()}`;

            let deleteButton =
                document.createElement("button");
            deleteButton.className = "delete-appt";
            deleteButton.textContent = "Delete";
            deleteButton.onclick = function () {
                deleteAppt(event.id);
            };

            listItem.appendChild(deleteButton);
            apptList.appendChild(listItem);
        }
    }
}

function generate_year_range(start, end) {
    let years = "";
    for (let year = start; year <= end; year++) {
        years += "<option value='" +
            year + "'>" + year + "</option>";
    }
    return years;
}

today = new Date();
currentMonth = today.getMonth();
currentYear = today.getFullYear();
selectYear = document.getElementById("year");
selectMonth = document.getElementById("month");

createYear = generate_year_range(1970, 2050);

document.getElementById("year").innerHTML = createYear;

let calendar = document.getElementById("calendar");

let months = [
    "January",
    "February",
    "March",
    "April",
    "May",
    "June",
    "July",
    "August",
    "September",
    "October",
    "November",
    "December"
];
let days = [
    "Sun", "Mon", "Tue", "Wed",
    "Thu", "Fri", "Sat"];

$dataHead = "<tr>";
for (dhead in days) {
    $dataHead += "<th data-days='" +
        days[dhead] + "'>" +
        days[dhead] + "</th>";
}
$dataHead += "</tr>";

document.getElementById("thead-month").innerHTML = $dataHead;

monthAndYear =
    document.getElementById("monthAndYear");
showCalendar(currentMonth, currentYear);

// Function to navigate to the next month
function next() {
    currentYear = currentMonth === 11 ?
        currentYear + 1 : currentYear;
    currentMonth = (currentMonth + 1) % 12;
    showCalendar(currentMonth, currentYear);
}

// Function to navigate to the previous month
function previous() {
    currentYear = currentMonth === 0 ?
        currentYear - 1 : currentYear;
    currentMonth = currentMonth === 0 ?
        11 : currentMonth - 1;
    showCalendar(currentMonth, currentYear);
}

function jump() {
    currentYear = parseInt(selectYear.value);
    currentMonth = parseInt(selectMonth.value);
    showCalendar(currentMonth, currentYear);
}

function showCalendar(month, year) {
    let firstDay = new Date(year, month, 1).getDay();
    tbl = document.getElementById("calendar-body");
    tbl.innerHTML = "";
    monthAndYear.innerHTML = months[month] + " " + year;
    selectYear.value = year;
    selectMonth.value = month;

    let date = 1;
    for (let i = 0; i < 6; i++) {
        let row = document.createElement("tr");
        for (let j = 0; j < 7; j++) {
            if (i === 0 && j < firstDay) {
                cell = document.createElement("td");
                cellText = document.createTextNode("");
                cell.appendChild(cellText);
                row.appendChild(cell);
            } else if (date > daysInMonth(month, year)) {
                break;
            } else {
                cell = document.createElement("td");
                cell.setAttribute("data-date", date);
                cell.setAttribute("data-month", month + 1);
                cell.setAttribute("data-year", year);
                cell.setAttribute("data-month_name", months[month]);
                cell.className = "date-picker";
                cell.innerHTML = "<span>" + date + "</span";

                if (
                    date === today.getDate() &&
                    year === today.getFullYear() &&
                    month === today.getMonth()
                ) {
                    cell.className = "date-picker selected";
                }

                // Check if there are events on this date
                if (hasEventOnDate(date, month, year)) {
                    cell.classList.add("event-marker");
                    cell.appendChild(
                        createEventTooltip(date, month, year)
                    );
                }

                row.appendChild(cell);
                date++;
            }
        }
        tbl.appendChild(row);
    }

    displayReminders();
}

function createEventTooltip(date, month, year) {
    let tooltip = document.createElement("div");
    tooltip.className = "event-tooltip";
    let eventsOnDate = getEventsOnDate(date, month, year);
    for (let i = 0; i < eventsOnDate.length; i++) {
        let event = eventsOnDate[i];
        let eventDate = new Date(event.date);
        let eventText = `<strong>${event.title}</strong> - 
			${event.description} on 
			${eventDate.toLocaleDateString()}`;
        let eventElement = document.createElement("p");
        eventElement.innerHTML = eventText;
        tooltip.appendChild(eventElement);
    }
    return tooltip;
}

function getEventsOnDate(date, month, year) {
    return events.filter(function (event) {
        let eventDate = new Date(event.date);
        return (
            eventDate.getDate() === date &&
            eventDate.getMonth() === month &&
            eventDate.getFullYear() === year
        );
    });
}
function mergeDateTime() {
    var date = document.getElementById("appointmentDate").value;
    var time = document.getElementById("appointmentTime").value;
    document.getElementById("hiddenApptTime").value = date + "T" + time;
}
function hasEventOnDate(date, month, year) {
    return getEventsOnDate(date, month, year).length > 0;
}

function daysInMonth(iMonth, iYear) {
    return 32 - new Date(iYear, iMonth, 32).getDate();
}

showCalendar(currentMonth, currentYear);
