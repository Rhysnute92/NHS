
let appointments = [];

const appointmentDateInput = document.getElementById("apptDate");
const appointmentTimeInput = document.getElementById("apptTime");
const appointmentTypeInput = document.getElementById("apptType");
const appointmentProviderInput = document.getElementById("apptProvider");
const appointmentInfoInput = document.getElementById("apptInfo");
const appointmentList = document.getElementById("apptList");

const appointmentForm = document.getElementById("apptForm");
appointmentForm.addEventListener("submit", function (event) {
    event.preventDefault();
    addAppointment();
});

async function addAppointment() {
    const appointment = {
        date: appointmentDateInput.value,
        time: appointmentTimeInput.value,
        appointmentType: appointmentTypeInput.value,
        provider: appointmentProviderInput.value,
        description: appointmentInfoInput.value
    };

    const url = "/appointment";
    try {
        const response = await fetch(url, {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(appointment)
        });
        if (response.status === 201) {
            const newAppointment = await response.json();
            appointments.push(newAppointment);
            appointmentDateInput.value = "";
            appointmentTimeInput.value = "";
            appointmentTypeInput.value = "";
            appointmentProviderInput.value = "";
            appointmentInfoInput.value = "";
        }
    } catch (error) {
        console.error("Error:", error);
    }

}

function deleteAppointment(appointmentID) {
    let appointmentIndex =
        appointments.findIndex((appointment) =>
            appointment.id === appointmentID);

    if (appointmentIndex !== -1) {
        appointments.splice(appointmentIndex, 1);
        showCalendar(currentMonth, currentYear);
        appointmentSchedule();
    }
}

function appointmentSchedule() {
    appointmentList.innerHTML = "";
    for (let i = 0; i < appointments.length; i++) {
        let appointment = appointments[i];
        let appointmentDate = new Date(appointment.date);
        if (appointmentDate.getMonth() ===
            currentMonth &&
            appointmentDate.getFullYear() ===
            currentYear) {
            let listItem = document.createElement("li");
            listItem.innerHTML =
                `<strong>${appointment.title}</strong> - 
			${appointment.description} on 
			${appointmentDate.toLocaleDateString()}`;

            let deleteButton =
                document.createElement("button");
            deleteButton.className = "delete-appointment";
            deleteButton.textContent = "Delete";
            deleteButton.onclick = function () {
                deleteAppointment(appointment.id);
            };

            listItem.appendChild(deleteButton);
            appointmentList.appendChild(listItem);
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

                // Check if there are appointments on this date
                if (hasappointmentOnDate(date, month, year)) {
                    cell.classList.add("appt-marker");
                    cell.appendChild(
                        createappointmentTooltip(date, month, year)
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

function createappointmentTooltip(date, month, year) {
    let tooltip = document.createElement("div");
    tooltip.className = "appt-tooltip";
    let appointmentsOnDate = getappointmentsOnDate(date, month, year);
    for (let i = 0; i < appointmentsOnDate.length; i++) {
        let appointment = appointmentsOnDate[i];
        let appointmentDate = new Date(appointment.date);
        let appointmentText = `<strong>${appointment.title}</strong> - 
			${appointment.description} on 
			${appointmentDate.toLocaleDateString()}`;
        let appointmentElement = document.createElement("p");
        appointmentElement.innerHTML = appointmentText;
        tooltip.appendChild(appointmentElement);
    }
    return tooltip;
}

function getappointmentsOnDate(date, month, year) {
    return appointments.filter(function (appointment) {
        let appointmentDate = new Date(appointment.date);
        return (
            appointmentDate.getDate() === date &&
            appointmentDate.getMonth() === month &&
            appointmentDate.getFullYear() === year
        );
    });
}

function hasappointmentOnDate(date, month, year) {
    return getappointmentsOnDate(date, month, year).length > 0;
}

function daysInMonth(iMonth, iYear) {
    return 32 - new Date(iYear, iMonth, 32).getDate();
}

showCalendar(currentMonth, currentYear);
