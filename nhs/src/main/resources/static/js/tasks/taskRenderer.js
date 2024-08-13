import {
  createCheckCircle,
  toggleCheckCircle,
} from "../common/utils/circleCheckmark.js";
import { detectMobileView } from "../common/utils/deviceUtils.js";

export class TaskRenderer {
  constructor() {
    this.isMobileView = detectMobileView();
  }
  /**
   * Creates and returns a table row element with the provided cells.
   * @param {Array<HTMLElement>} cells - The cells to be included in the row.
   * @return {HTMLElement} - The created table row.
   */
  createTableRow(cells) {
    const row = document.createElement("tr");
    cells.forEach((cell) => row.appendChild(cell));
    return row;
  }

  /**
   * Creates and returns a table cell with the specified content and class.
   * @param {string} content - The text content of the cell.
   * @param {string} className - The class name to apply to the cell.
   * @return {HTMLElement} - The created table cell.
   */
  createTableCell(content, className) {
    const cell = document.createElement("td");
    cell.classList.add(className);
    cell.textContent = content;
    return cell;
  }

  /**
   * Creates and returns a divider row for the table.
   * @return {HTMLElement} - The divider row element.
   */
  createDividerRow() {
    const dividerRow = document.createElement("tr");
    const dividerCell = document.createElement("td");
    dividerCell.colSpan = 4;
    const divider = document.createElement("hr");
    divider.classList.add("task-divider");
    dividerCell.appendChild(divider);
    dividerRow.appendChild(dividerCell);
    return dividerRow;
  }

  /**
   * Creates and returns the cell containing the check circle.
   * @param {Task} task - The task object.
   * @return {HTMLElement} - The cell with the check circle.
   */
  createCheckCircleCell(task, taskCard) {
    const checkCircleCell = document.createElement("td");
    const checkCircleWrapper = createCheckCircle();
    checkCircleWrapper.addEventListener("click", () => {
      toggleCheckCircle(checkCircleWrapper);
      task.status = task.status === "Complete" ? "Incomplete" : "Complete"; // Toggle status (only of js task object; db record updated later)
      this.updateTaskCompletionUI(task, taskCard); // Update the UI to reflect the status
    });
    checkCircleCell.appendChild(checkCircleWrapper);
    return checkCircleCell;
  }

  /**
   * Updates the UI to reflect the completion status of the task.
   * @param {Task} task - The task object.
   * @param {HTMLElement} taskCard - The task card element.
   */
  updateTaskCompletionUI(task, taskCard) {
    if (task.status === "Complete") {
      taskCard.classList.add("completed");
      const descElement = taskCard.querySelector(".task-desc");
      const statusElement = taskCard.querySelector(".task-status");
      if (descElement) descElement.classList.add("completed");
      if (statusElement) statusElement.textContent = "Complete";
    } else {
      taskCard.classList.remove("completed");
      const descElement = taskCard.querySelector(".task-desc");
      const statusElement = taskCard.querySelector(".task-status");
      if (descElement) descElement.classList.remove("completed");
      if (statusElement) statusElement.textContent = "Incomplete";
    }
  }

  /**
   * Renders the task card as an HTML element based on screen size.
   * @param {Task} task - The task object to render.
   * @return {HTMLElement} - The task card element.
   */
  renderTaskCard(task) {
    if (this.isMobileView) {
      console.log("Rendering mobile task card...");
      return this.renderMobileTaskCard(task);
    } else {
      console.log("Rendering desktop task card...");
      return this.renderDesktopTaskCard(task);
    }
  }

  /**
   * Renders the task card for desktop view.
   * @param {Task} task - The task object to render.
   * @return {HTMLElement} - The task card element.
   */
  renderDesktopTaskCard(task) {
    const taskCard = document.createElement("div");
    taskCard.classList.add("task-card");

    const table = document.createElement("table");
    table.classList.add("task-table");

    // Header row
    const headerRow = this.createTableRow([
      this.createTableCell(task.name, "task-name-header"),
      this.createTableCell("Schedule", "meta-title"),
      this.createTableCell("Status", "meta-title"),
      this.createTableCell("Complete", "meta-title"),
    ]);

    // Divider row
    const dividerRow = this.createDividerRow();

    // Data row
    const dataRow = this.createTableRow([
      this.createTableCell(task.description, "task-desc"),
      this.createTableCell(task.formatPeriodicity(), "task-periodicity"),
      this.createTableCell(task.status, "task-status"),
      this.createCheckCircleCell(task, taskCard),
    ]);

    // Append rows to table
    table.append(headerRow, dividerRow, dataRow);

    // Append table to task card
    taskCard.appendChild(table);

    // Update the UI to reflect the status
    this.updateTaskCompletionUI(task, taskCard);

    return taskCard;
  }

  /**
   * Renders the task card for mobile view.
   * @param {Task} task - The task object to render.
   * @return {HTMLElement} - The task card element.
   */
  renderMobileTaskCard(task) {
    const taskCard = document.createElement("div");
    taskCard.classList.add("task-card");

    // Mobile header with expand icon, title, and checkmark
    const header = document.createElement("div");
    header.classList.add("task-header");

    // Expand icon
    const expandIcon = document.createElement("div");
    expandIcon.classList.add("expand-icon");
    expandIcon.addEventListener("click", () => {
      taskCard.classList.toggle("expanded");
      const description = taskCard.querySelector(".task-desc");
      description.style.display = taskCard.classList.contains("expanded")
        ? "block"
        : "none";
    });

    // Title in the center
    const title = document.createElement("div");
    title.classList.add("task-name-header");
    title.textContent = task.name;

    // Check circle on the right
    const checkCircle = this.createCheckCircleCell(task, taskCard);
    checkCircle.classList.add("check-circle-mobile");

    // Append expand icon, title, and checkCircle to header
    header.appendChild(expandIcon);
    header.appendChild(title);
    header.appendChild(checkCircle);

    // Mobile description (hidden by default)
    const description = document.createElement("div");
    description.classList.add("task-desc");
    description.textContent = task.description;

    // Append header and description to task card
    taskCard.appendChild(header);
    taskCard.appendChild(description);

    // Update the UI to reflect the status
    this.updateTaskCompletionUI(task, taskCard);

    return taskCard;
  }
}
