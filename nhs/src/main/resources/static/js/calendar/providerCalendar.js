const apptModal = document.querySelector('.appt-modal');
// DOM elements for appointment form fields
const apptDateInput = document.getElementById("apptDate");
const apptTimeInput = document.getElementById("apptTime");
const apptTypeInput = document.getElementById("apptType");
const apptProviderInput = document.getElementById("apptProvider");
const apptInfoInput = document.getElementById("apptInfo");
const apptList = document.getElementById("apptList");
const noApptMessage = document.querySelector(".no-appointments");

// DOM element for appointment form
const apptForm = document.getElementById("apptForm");

// Event listener for the appointment form submission
if (apptForm) {
    apptForm.addEventListener("submit", function (evt) {
        evt.preventDefault();
        addAppointment().then(() => {
            apptModal.hide();
        });
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
            if (!window.location.href.includes("calendar")) {
                window.location.href = "/calendar";
            }

            const newAppointment = await response.json();
            appointments.push(newAppointment);
            displayAppointment(newAppointment);
            apptForm.reset();

            // Update calendar and display appointments
            showCalendar(currentMonth, currentYear);
        }
    } catch (error) {
        console.error("Error:", error);
    }
}

// Function to delete an appointment
function deleteAppointment(appointmentID) {
    let appointmentIndex = appointments.findIndex((appointment) => appointment.id === appointmentID);

    if (appointmentIndex !== -1) {
        document.querySelector(`[data-id="${appointmentID}"]`).remove();

        if (apptList.children.length > 0) {
            noApptMessage.style.display = "none";
        } else {
            noApptMessage.style.display = "block";
        }

        // Update calendar and display appointments
        showCalendar(currentMonth, currentYear);
    }
}


// Function to display appointments in the list
function displayAppointment(appointment) {
    let apptDate = new Date(appointment.apptDateTime);
    if (apptDate.getMonth() === currentMonth && apptDate.getFullYear() === currentYear) {
        let listItem = document.createElement("li");
        listItem.innerHTML = `
                <span>
                    <strong><span>${appointment.apptType}</span></strong>
                    - <span>${appointment.apptInfo}</span>
                    on <span>${apptDate.toLocaleDateString()}</span>
                    at <span>${apptDate.toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' })}</span>
                </span>            
              `;
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

        if (apptList.children.length > 0) {
            noApptMessage.style.display = "none";
        } else {
            noApptMessage.style.display = "block";
        }
    }
}

if (apptModal) {
    apptModal.addEventListener('close', () => {
        apptForm.reset();
    });

    function showList() {
        document.getElementById('appointment-list1').style.display = 'block'; // Show the list
        document.getElementById('calendar1').style.display = 'none'; // Hide the calendar
        document.getElementById('no-appointments').style.display = 'none'; // Hide "No appointments" message
        document.getElementById('addAppointment').style.display = 'none';
    }

    function showProviderCalendar() {
        document.getElementById('appointment-list1').style.display = 'none'; // Hide the list
        document.getElementById('calendar1').style.display = 'block'; // Show the calendar
        document.getElementById('no-appointments').style.display = 'none'; // Hide "No appointments" message
        document.getElementById('addAppointment').style.display = 'none';
    }
    function showProviderAddAppointment() {
        document.getElementById('appointment-list1').style.display = 'none'; // Show the list
        document.getElementById('calendar1').style.display = 'none'; // Hide the calendar
        document.getElementById('no-appointments').style.display = 'none'; // Hide "No appointments" message
        document.getElementById('addAppointment').style.display = 'block';
    }
// Initial setup to display the correct tab based on available data
    window.onload = function () {
        const appointmentsExist = document.querySelector('#appointment-list tbody tr');
        if (appointmentsExist) {
            showList(); // Show list by default if appointments exist
        } else {
            document.getElementById('appointment-list1').style.display = 'none';
            document.getElementById('calendar1').style.display = 'none';
            document.getElementById('no-appointments').style.display = 'block';
            document.getElementById('addAppointment').style.display = 'none';
        }
    }
}
