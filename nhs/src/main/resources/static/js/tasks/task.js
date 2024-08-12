import {
  createCheckCircle,
  toggleCheckCircle,
} from "../common/utils/circleCheckmark.js";
export class Task {
  /**
   * Creates an instance of Task.
   *
   * @param {number} id - The unique identifier for the task.
   * @param {string} name - The name of the task.
   * @param {string} description - The description of the task.
   * @param {number} bitmask - The bitmask indicating the completion status of the task.
   * @param {string} periodicity - The periodicity of the task (e.g., Daily, Weekly).
   */
  constructor(id, name, description, bitmask, periodicity) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.bitmask = bitmask;
    this.periodicity = periodicity;
    this.status = this.calculateStatus();
  }

  // Method to check the bit for the current day and set the status
  calculateStatus() {
    const currentDay = new Date().getDate();
    const isBitSet = (this.bitmask & (1 << (currentDay - 1))) !== 0;
    return isBitSet ? "Complete" : "Incomplete";
  }

  /*   createTaskFooter() {
    const taskFooter = document.createElement("div");
    taskFooter.classList.add("task-footer");

    const taskCompleteLabel = document.createElement("span");
    taskCompleteLabel.textContent = "Complete:";

    const checkCircleWrapper = createCheckCircle();

    checkCircleWrapper.addEventListener("click", () => {
      toggleCheckCircle(checkCircleWrapper);
      this.status = this.calculateStatus();
    });

    taskFooter.append(taskCompleteLabel, checkCircleWrapper);

    return taskFooter;
  } */

  /**
   * Renders a task item as an HTML element with the task's name, description, and status.
   *
   * @return {HTMLElement} The task item element.
   */
  renderTaskCard() {
    const taskCard = document.createElement("div");
    taskCard.classList.add("task-card");

    const table = document.createElement("table");
    table.classList.add("task-table");

    // Header row (Title, Schedule, Status, Complete)
    const headerRow = document.createElement("tr");

    const taskNameHeader = document.createElement("td");
    taskNameHeader.classList.add("task-name-header");
    taskNameHeader.textContent = this.name;

    const scheduleHeader = document.createElement("td");
    scheduleHeader.classList.add("meta-title");
    scheduleHeader.textContent = "Schedule";

    const statusHeader = document.createElement("td");
    statusHeader.classList.add("meta-title");
    statusHeader.textContent = "Status";

    const completeHeader = document.createElement("td");
    completeHeader.classList.add("meta-title");
    completeHeader.textContent = "Complete";

    headerRow.append(
      taskNameHeader,
      scheduleHeader,
      statusHeader,
      completeHeader
    );

    // Divider row
    const dividerRow = document.createElement("tr");
    const dividerCell = document.createElement("td");
    dividerCell.colSpan = 4;
    const divider = document.createElement("hr");
    divider.classList.add("task-divider");
    dividerCell.appendChild(divider);
    dividerRow.appendChild(dividerCell);

    // Data row (Description, Periodicity, Status, Check Circle)
    const dataRow = document.createElement("tr");

    const taskDescCell = document.createElement("td");
    taskDescCell.classList.add("task-desc");
    taskDescCell.textContent = this.description;

    const periodicityCell = document.createElement("td");
    periodicityCell.classList.add("task-periodicity");
    periodicityCell.textContent =
      this.periodicity.charAt(0).toUpperCase() +
      this.periodicity.slice(1).toLowerCase(); // Title case

    const statusCell = document.createElement("td");
    statusCell.classList.add("task-status");
    statusCell.textContent = this.status;

    const checkCircleCell = document.createElement("td");
    const checkCircleWrapper = createCheckCircle();
    checkCircleWrapper.addEventListener("click", () => {
      toggleCheckCircle(checkCircleWrapper);
      this.status = this.calculateStatus();
    });
    checkCircleCell.appendChild(checkCircleWrapper);

    dataRow.append(taskDescCell, periodicityCell, statusCell, checkCircleCell);

    // Append rows to table
    table.append(headerRow, dividerRow, dataRow);

    // Append table to task card
    taskCard.appendChild(table);

    return taskCard;
  }
}
