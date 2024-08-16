// addappointments.js

// Initialize an empty array to hold the appointments
let appointments = [];

const apptModal = document.getElementById("myModel");
// Get references to the form input elements
const apptDateInput = document.getElementById("apptDate");
const apptTimeInput = document.getElementById("apptTime");
const apptTypeInput = document.getElementById("apptType");
const apptProviderInput = document.getElementById("apptProvider");
const apptInfoInput = document.getElementById("apptInfo");
const apptList = document.getElementById("apptList");

// Get the form element
const apptForm = document.getElementById("apptForm");

// Add event listener to the form to handle form submission
if (apptForm) {
    apptForm.addEventListener("submit", function (evt) {
        evt.preventDefault(); // Prevent the default form submission behavior
        addAppointment(); // Call the function to add the appointment
    });
}

// Function to add a new appointment
async function addAppointment() {
    // Create an appointment object with the values from the form inputs
    const appointment = {
        date: apptDateInput.value,
        time: apptTimeInput.value,
        appointmentType: apptTypeInput.value,
        provider: apptProviderInput.value,
        description: apptInfoInput.value
    };

    const url = "/appointments"; // URL to which the appointment will be sent
    try {
        // Send a POST request to the server to save the appointment
        const response = await fetch(url, {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(appointment)
        });
        if (response.status === 201) { // If the appointment is successfully created
            const newAppointment = await response.json(); // Get the newly created appointment data
            appointments.push(newAppointment); // Add the new appointment to the array
            clearForm(); // Clear the form inputs
            displayAppointments(); // Display the updated list of appointments
        }
    } catch (error) {
        console.error("Error:", error); // Log any errors that occur during the request
    }
}

// Function to clear the form inputs
function clearForm() {
    apptDateInput.value = "";
    apptTimeInput.value = "";
    apptTypeInput.value = "";
    apptProviderInput.value = "";
    apptInfoInput.value = "";
    apptModal.style.display = "none"; // Hide the modal after the form is submitted
}

// Function to delete an appointment
function deleteAppointment(appointmentID) {
    // Find the index of the appointment to be deleted
    let appointmentIndex = appointments.findIndex((appointment) => appointment.id === appointmentID);

    if (appointmentIndex !== -1) { // If the appointment is found
        appointments.splice(appointmentIndex, 1); // Remove the appointment from the array
        displayAppointments(); // Update the displayed list of appointments
    }
}

// Function to display all the appointments in the list
function displayAppointments() {
    apptList.innerHTML = ""; // Clear the current list of appointments
    for (let appointment of appointments) {
        let apptDate = new Date(appointment.apptDateTime); // Convert the appointment date to a Date object
        let listItem = document.createElement("li"); // Create a new list item
        listItem.innerHTML = `<strong>${appointment.apptType}</strong> - ${appointment.apptInfo} on ${apptDate.toLocaleDateString()} at ${apptDate.toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' })}`;
        listItem.className = "appt-item"; // Add a class to the list item for styling

        // Create a delete button for each appointment
        let deleteButton = document.createElement("button");
        deleteButton.className = "delete-appt";
        deleteButton.textContent = "Delete";
        deleteButton.onclick = function () {
            deleteAppointment(appointment.id); // Call delete function when button is clicked
        };

        listItem.appendChild(deleteButton); // Add the delete button to the list item
        apptList.appendChild(listItem); // Add the list item to the appointment list
    }
}

// Function to fetch all appointments from the server
async function fetchAppointments() {
    try {
        const response = await fetch('/appointments'); // Fetch appointments from the server
        if (response.ok) { // If the request is successful
            appointments = await response.json(); // Store the fetched appointments in the array
            displayAppointments(); // Display the fetched appointments
        } else {
            console.error("Failed to fetch appointments"); // Log an error if the request fails
        }
    } catch (error) {
        console.error("Error:", error); // Log any errors that occur during the request
    }
}
