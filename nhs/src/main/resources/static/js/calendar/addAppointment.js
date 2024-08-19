// Array to store appointment data
let appointments = [];

const apptModal = document.querySelector('.appt-modal');
// DOM elements for appointment form fields
const apptDateInput = document.getElementById("apptDate");
const apptTimeInput = document.getElementById("apptTime");
const apptTypeInput = document.getElementById("apptType");
const apptProviderInput = document.getElementById("apptProvider");
const apptInfoInput = document.getElementById("apptInfo");
const apptList = document.getElementById("apptList");

// DOM element for appointment form
const apptForm = document.getElementById("apptForm");

// Event listener for the appointment form submission
if (apptForm) {
    apptForm.addEventListener("submit", function (evt) {
        evt.preventDefault();
        addAppointment();
    });
}

// Function to add an appointment
async function addAppointment() {
    // Create an appointment object from form inputs
    const appointment = {
        date: apptDateInput.value,
        time: apptTimeInput.value,
        appointmentType: apptTypeInput.value,
        provider: apptProviderInput.value,
        description: apptInfoInput.value
    };

    const url = "/appointments";
    try {
        // Send a POST request to add the appointment
        const response = await fetch(url, {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(appointment)
        });

        // Check if the request was successful
        if (response.status === 201) {
            // Redirect to calendar if on the appointment page
            if (window.location.href.includes("appt")) {
                window.location.href = "/calendar";
            }

            const newAppointment = await response.json();
            appointments.push(newAppointment);
            apptForm.reset();

            // Update calendar and display appointments
            if (typeof showCalendar === "function") {
                showCalendar(currentMonth, currentYear);
            }
            displayAppointments();
        }
    } catch (error) {
        console.error("Error:", error);
    }
}

// Function to delete an appointment
function deleteAppointment(appointmentID) {
    let appointmentIndex = appointments.findIndex((appointment) => appointment.id === appointmentID);

    if (appointmentIndex !== -1) {
        appointments.splice(appointmentIndex, 1);

        // Update calendar and display appointments
        if (typeof showCalendar === "function") {
            showCalendar(currentMonth, currentYear);
        }
        displayAppointments();
    }
}

// Function to display appointments in the list
function displayAppointments() {
    apptList.innerHTML = "";
    for (let appointment of appointments) {
        let apptDate = new Date(appointment.apptDateTime);
        if (apptDate.getMonth() === currentMonth && apptDate.getFullYear() === currentYear) {
            let listItem = document.createElement("li");
            listItem.innerHTML = `<strong>${appointment.apptType}</strong> - ${appointment.apptInfo} on ${apptDate.toLocaleDateString()} at ${apptDate.toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' })}`;
            listItem.className = "appt-item";

            // Create a delete button for each appointment
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

apptModal.addEventListener('close', () => {
    apptForm.reset();
});