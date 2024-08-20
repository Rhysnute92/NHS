import { Appointment } from "./appointment.js"; // Import the Appointment class
import { AppointmentRenderer } from "./appointmentRenderer.js"; // Import the AppointmentRenderer class

export class AppointmentManager {
    constructor(eventQueue, appointmentRenderer) {
        this.appointments = [];
        this.appointmentRenderer = appointmentRenderer || new AppointmentRenderer(eventQueue); // Allow injection of an AppointmentRenderer instance
    }

    async fetchAppointments(date = null) {
        try {
            // Construct the URL with the optional date parameter
            let url = '/userappointments/user';
            if (date) {
                url += `?date=${date}`;
            }

            const response = await fetch(url, {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json'
                }
            });

            if (!response.ok) {
                throw new Error(`Error fetching appointments: ${response.statusText}`);
            }

            const appointmentData = await response.json();

            this.appointments = appointmentData.map(
                (userAppointment) =>
                    new Appointment(
                        userAppointment.id,
                        userAppointment.name,
                        userAppointment.description,
                        userAppointment.dateTime,
                        userAppointment.status
                    )
            );

            console.debug("Fetched appointments: ", this.appointments);
            return this.appointments; // Return appointments so they can be used externally if needed
        } catch (error) {
            console.error("Error fetching appointments:", error);
            throw error; // Re-throw the error to handle it externally
        }
    }

    renderAppointments(container) {
        this.clearContainer(container);
        console.debug("Rendering appointments...");
        this.appointments.forEach((appointment) => {
            container.appendChild(this.appointmentRenderer.renderAppointmentCard(appointment));
        });
    }

    displayAppointmentErrorMessage(container) {
        this.clearContainer(container);

        const errorMessage = document.createElement("p");
        errorMessage.classList.add("error-message");
        errorMessage.textContent = "Unable to load appointments. Please try again later.";
        container.appendChild(errorMessage);
        console.debug("Displayed appointment error message.");
    }

    clearContainer(container) {
        if (container) {
            container.innerHTML = "";
            console.debug("Cleared appointments container.");
        } else {
            console.error("No container provided to clear.");
        }
    }
}
