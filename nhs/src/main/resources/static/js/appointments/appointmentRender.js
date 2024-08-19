import {
    addEventListener,
    toggleClass,
    updateElementDisplay,
} from "../common/utils/eventUtility.js";
import { detectMobileView } from "../common/utils/deviceUtils.js";

export class AppointmentRenderer {
    constructor(eventQueue, userId) {
        this.isMobileView = detectMobileView();
        this.eventQueue = eventQueue;
    }

    createTableRow(cells) {
        const row = document.createElement("tr");
        cells.forEach((cell) => row.appendChild(cell));
        return row;
    }

    createTableCell(content, className) {
        const cell = document.createElement("td");
        cell.classList.add(className);
        cell.textContent = content;
        return cell;
    }

    createDividerRow() {
        const dividerRow = document.createElement("tr");
        const dividerCell = document.createElement("td");
        dividerCell.colSpan = 4;
        const divider = document.createElement("hr");
        divider.classList.add("appointment-divider");
        dividerCell.appendChild(divider);
        dividerRow.appendChild(dividerCell);
        return dividerRow;
    }

    createCheckCircleCell(appointment, appointmentCard) {
        const checkCircleCell = document.createElement("div");
        const checkCircleWrapper = createCheckCircle();

        if (appointment.status === "Completed") {
            checkCircleWrapper.classList.add("checked");
            appointmentCard.classList.add("completed");
        }

        addEventListener(checkCircleWrapper, "click", () => {
            toggleCheckCircle(checkCircleWrapper);

            // Toggle the appointment's completion status
            appointment.toggleCompletion();
            console.debug(
                `Toggled appointment completion: ${appointment.name}, new status: ${appointment.status}`
            );

            // Update the UI to reflect the new status
            this.updateAppointmentCompletionUI(appointment, appointmentCard);

            // Add the updated UserAppointment to the event queue
            const userAppointment = appointment.toUserAppointment();
            this.eventQueue.addEvent(userAppointment.id, userAppointment);
            console.log("Added appointment to event queue:", userAppointment);
        });

        checkCircleCell.appendChild(checkCircleWrapper);
        return checkCircleCell;
    }

    updateAppointmentCompletionUI(appointment, appointmentCard) {
        if (appointment.status === "Completed") {
            appointmentCard.classList.add("completed");
            const descElement = appointmentCard.querySelector(".appointment-desc");
            const statusElement = appointmentCard.querySelector(".appointment-status");
            if (descElement) descElement.classList.add("completed");
            if (statusElement) statusElement.textContent = "Completed";
        } else {
            appointmentCard.classList.remove("completed");
            const descElement = appointmentCard.querySelector(".appointment-desc");
            const statusElement = appointmentCard.querySelector(".appointment-status");
            if (descElement) descElement.classList.remove("completed");
            if (statusElement) statusElement.textContent = "Upcoming";
        }
        console.debug(`Updated appointment UI for: ${appointment.name}, status: ${appointment.status}`);
    }

    renderAppointmentCard(appointment) {
        if (this.isMobileView) {
            console.debug("Rendering mobile appointment card...");
            return this.renderMobileAppointmentCard(appointment);
        } else {
            console.debug("Rendering desktop appointment card...");
            return this.renderDesktopAppointmentCard(appointment);
        }
    }

    renderDesktopAppointmentCard(appointment) {
        const appointmentCard = document.createElement("div");
        appointmentCard.classList.add("appointment-card");

        const table = document.createElement("table");
        table.classList.add("appointment-table");

        const headerRow = this.createTableRow([
            this.createTableCell(appointment.name, "appointment-name-header"),
            this.createTableCell("Date & Time", "meta-title"),
            this.createTableCell("Status", "meta-title"),
            this.createTableCell("Completed", "meta-title"),
        ]);

        const dividerRow = this.createDividerRow();

        const dataRow = this.createTableRow([
            this.createTableCell(appointment.description, "appointment-desc"),
            this.createTableCell(appointment.formatDateTime(), "appointment-datetime"),
            this.createTableCell(appointment.status, "appointment-status"),
            this.createCheckCircleCell(appointment, appointmentCard),
        ]);

        table.append(headerRow, dividerRow, dataRow);
        appointmentCard.appendChild(table);
        this.updateAppointmentCompletionUI(appointment, appointmentCard);

        return appointmentCard;
    }

    renderMobileAppointmentCard(appointment) {
        const appointmentCard = document.createElement("div");
        appointmentCard.classList.add("appointment-card");

        const header = document.createElement("div");
        header.classList.add("appointment-header");

        const expandIcon = document.createElement("div");
        expandIcon.classList.add("expand-icon");
        addEventListener(expandIcon, "click", () => {
            toggleClass(appointmentCard, "expanded");
            const description = appointmentCard.querySelector(".appointment-desc");
            updateElementDisplay(
                description,
                appointmentCard.classList.contains("expanded") ? "block" : "none"
            );
        });

        const title = document.createElement("div");
        title.classList.add("appointment-name-header");
        title.textContent = appointment.name;

        const checkCircle = this.createCheckCircleCell(appointment, appointmentCard);
        checkCircle.classList.add("check-circle-mobile");

        header.append(expandIcon, title, checkCircle);
        const description = document.createElement("div");
        description.classList.add("appointment-desc");
        description.textContent = appointment.description;

        appointmentCard.append(header, description);
        this.updateAppointmentCompletionUI(appointment, appointmentCard);

        return appointmentCard;
    }

    renderAppointmentWidgetAppointments(appointments, appointmentListContainer) {
        // Clear any existing appointments
        appointmentListContainer.innerHTML = "";

        // Render each appointment as a simple row in the table
        appointments.forEach((appointment) => {
            const row = document.createElement("tr");

            // Appointment name cell
            const nameCell = this.createTableCell(appointment.name, "appointment-widget-name");
            row.appendChild(nameCell);

            // Appointment status cell
            const statusCell = this.createTableCell(
                appointment.status,
                "appointment-widget-status"
            );
            row.appendChild(statusCell);

            // Completion toggle cell
            const toggleCell = document.createElement("td");
            const toggleButton = document.createElement("button");
            toggleButton.textContent = appointment.status === "Completed" ? "✔️" : "❌";

            toggleButton.addEventListener("click", () => {
                appointment.toggleCompletion();
                toggleButton.textContent = appointment.status === "Completed" ? "✔️" : "❌";
                this.updateAppointmentCompletionUI(appointment, row);
            });

            toggleCell.appendChild(toggleButton);
            row.appendChild(toggleCell);

            // Append the row to the container
            appointmentListContainer.appendChild(row);
        });
    }

    renderPopupAppointmentCard(appointment) {
        const appointmentCard = document.createElement("div");
        appointmentCard.classList.add("popup-appointment-card");

        const title = document.createElement("span");
        title.textContent = appointment.name;
        title.classList.add("popup-appointment-title");
        appointmentCard.appendChild(title);

        const checkCircle = this.createCheckCircleCell(appointment, appointmentCard);
        checkCircle.classList.add("popup-check-circle");
        appointmentCard.appendChild(checkCircle);

        return appointmentCard;
    }

    renderAppointmentPopup(appointments, appointmentListContainer) {
        appointmentListContainer.innerHTML = ""; // Clear existing appointments

        appointments.forEach((appointment) => {
            const appointmentCard = this.renderPopupAppointmentCard(appointment);
            appointmentListContainer.appendChild(appointmentCard);
        });
    }
}
