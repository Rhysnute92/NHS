import { AppointmentRenderer } from "../appointments/appointmentRenderer.js";
import { AppointmentManager } from "../appointments/appointmentManager.js";
import { EventQueue } from "../appointments/eventQueue.js";

export class AppointmentWidget {
    constructor() {
        this.widgetElement = document.querySelector(".widget-appointment-list");
        if (!this.widgetElement) {
            console.error("Appointment widget element not found.");
            return;
        }
        console.log("Widget Element:", this.widgetElement);

        // Initialize EventQueue and AppointmentManager with an AppointmentRenderer
        this.eventQueue = new EventQueue();
        this.appointmentRenderer = new AppointmentRenderer(this.eventQueue, null);
        this.appointmentManager = new AppointmentManager(this.eventQueue, this.appointmentRenderer);

        this.worker = new Worker("/js/appointments/worker.js"); // Web Worker for async appointment updates
        this.initializePopup();

        // Periodically send the event queue to the Web Worker for processing
        setInterval(() => {
            if (this.eventQueue.getSize() > 0) {
                console.log(
                    "Sending event queue to worker:",
                    this.eventQueue.getEvents()
                );
                this.worker.postMessage({
                    queue: this.eventQueue.getEvents(),
                    apiUrl: "/userappointment/appointment-update/batch",
                });

                // Clear the queue after sending it to the worker
                this.eventQueue.clearQueue();
            }
        }, 1000);

        // Handle messages from the Web Worker
        this.worker.addEventListener(
            "message",
            function (event) {
                if (event.data.status === "success") {
                    console.log("Events synced successfully:", event.data.data);
                } else if (event.data.status === "error") {
                    console.error("Failed to sync events:", event.data.error);
                }
            },
            false
        );
    }

    async updateWidgetData() {
        if (!this.widgetElement) return;

        try {
            const response = await fetch("/api/widgets/appointment-list/data");
            if (response.ok) {
                const data = await response.json();
                console.log("API data:", data);
                this.updateAppointmentList(data.completedAppointments, data.totalAppointments);
            } else {
                console.error("Failed to fetch appointment data");
            }
        } catch (error) {
            console.error("Error fetching appointment data:", error);
        }
    }

    updateAppointmentList(completedAppointments, totalAppointments) {
        if (!this.widgetElement) {
            console.error("Appointment widget element not found during update.");
            return;
        }

        console.log("Updating appointment list with", completedAppointments, totalAppointments);
        
        const progressText = this.widgetElement.querySelector("#completedAppointments");
        const totalText = this.widgetElement.querySelector("#totalAppointments");

        // Update the text content
        progressText.textContent = completedAppointments;
        totalText.textContent = totalAppointments;
    }

    async initializePopup() {
        const completeAppointmentButton = this.widgetElement.querySelector(
            ".complete-appointment-button"
        );
        const appointmentPopup = this.widgetElement.querySelector("#appointmentPopup");
        const appointmentPopupOverlay =
            this.widgetElement.querySelector("#appointmentPopupOverlay");
        const donePopupButton = appointmentPopup.querySelector("#DonePopupButton");
        const appointmentListContainer = appointmentPopup.querySelector("#appointmentListContainer");

        if (
            !completeAppointmentButton ||
            !appointmentPopup ||
            !donePopupButton ||
            !appointmentListContainer
        ) {
            console.error("Popup elements not found.");
            return;
        }

        // Ensure the popup is hidden initially
        appointmentPopupOverlay.style.display = "none";
        appointmentPopup.style.display = "none";

        console.log("Popup hidden on initialization");

        // Show the popup and load appointments
        completeAppointmentButton.addEventListener("click", async () => {
            console.log("Complete appointment button clicked");
            appointmentPopupOverlay.style.display = "block"; // Show overlay
            appointmentPopup.style.display = "block"; // Show popup

            // Fetch and render appointments
            await this.fetchAndRenderAppointments(appointmentListContainer);
        });

        // Hide the popup and overlay when the close button or overlay is clicked
        const closePopup = async () => {
            console.log("Close popup triggered");
            appointmentPopupOverlay.style.display = "none"; // Hide overlay
            appointmentPopup.style.display = "none"; // Hide popup

            await this.updateWidgetData();
        };

        donePopupButton.addEventListener("click", closePopup);
        appointmentPopupOverlay.addEventListener("click", closePopup);
    }

    async fetchAndRenderAppointments(appointmentListContainer) {
        try {
            await this.appointmentManager.fetchAppointments();
            this.appointmentRenderer.renderAppointmentPopup(
                this.appointmentManager.appointments,
                appointmentListContainer
            ); // Pass the specific container for rendering
        } catch (error) {
            console.error("Error fetching and rendering appointments:", error);
            this.appointmentManager.displayAppointmentErrorMessage(appointmentListContainer);
        }
    }
}
