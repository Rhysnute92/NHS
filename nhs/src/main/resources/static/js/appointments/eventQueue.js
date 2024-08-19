import { formatStringTitleCase } from "../common/utils/stringUtility.js";

export class Appointment {
    /**
     * Creates an instance of Appointment.
     *
     * @param {number} id - The unique identifier for the appointment.
     * @param {string} name - The name of the appointment.
     * @param {string} description - The description of the appointment.
     * @param {string} dateTime - The date and time of the appointment.
     * @param {string} status - The status of the appointment (e.g., Scheduled, Completed, Canceled).
     */
    constructor(id, name, description, dateTime, status) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.dateTime = new Date(dateTime); // Store as a Date object
        this.status = status;
    }

    /**
     * Updates the status of the appointment.
     * @param {string} newStatus - The new status of the appointment.
     */
    updateStatus(newStatus) {
        this.status = newStatus;
        console.log(`Updated appointment status: ${this.name}, new status: ${this.status}`);
    }

    /**
     * Formats the date and time of the appointment for display.
     * @return {string} - The formatted date and time.
     */
    formatDateTime() {
        return this.dateTime.toLocaleString(); // Customize formatting as needed
    }

    /**
     * Converts the appointment object to a format suitable for API or other uses.
     * @return {object} - The appointment data as an object.
     */
    toAppointmentData() {
        const appointmentData = {
            id: this.id,
            name: this.name,
            description: this.description,
            dateTime: this.dateTime.toISOString(), // Convert Date object to ISO string
            status: this.status,
        };

        // Debugging: Log the constructed Appointment object
        console.log("Constructed AppointmentData:", appointmentData);

        return appointmentData;
    }
}
