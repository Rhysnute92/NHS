import { Appointment } from "./appointment.js"; // Import the Appointment class
import { AppointmentRenderer } from "./appointmentRenderer.js"; // Import the AppointmentRenderer class
import { fetchData } from "../common/utils/apiUtility.js"; // Utility for API calls

export class AppointmentManager {
    constructor(eventQueue, appointmentRenderer) {
        this.appointments = [];
        this.appointmentRenderer = appointmentRenderer || new AppointmentRenderer(eventQueue); // Allow injection of an AppointmentRenderer instance
        this.eventQueue = eventQueue;
    }

    async fetchAppointments() {
        try {
            const appointmentData = await fetchData(`/userappointments/user`);
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
