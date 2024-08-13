import {
  createCheckCircle,
  toggleCheckCircle,
} from "../common/utils/circleCheckmark.js";

export class TaskRenderer {
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
   * Renders the task card as an HTML element.
   * @param {Task} task - The task object to render.
   * @return {HTMLElement} - The task card element.
   */
  renderTaskCard(task) {
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
}
