import { formatStringTitleCase } from "../common/utils/stringUtility.js";
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

  /**
   * Formats the periodicity to be title case.
   * @return {string} - The formatted periodicity.
   */
  formatPeriodicity() {
    return formatStringTitleCase(this.periodicity);
  }

  /**
   * Renders the task card as an HTML element.
   * @return {HTMLElement} - The task card element.
   */
  renderTaskCard() {
    const taskCard = document.createElement("div");
    taskCard.classList.add("task-card");

    const table = document.createElement("table");
    table.classList.add("task-table");

    // Header row
    const headerRow = this.createTableRow([
      this.createTableCell(this.name, "task-name-header"),
      this.createTableCell("Schedule", "meta-title"),
      this.createTableCell("Status", "meta-title"),
      this.createTableCell("Complete", "meta-title"),
    ]);

    // Divider row
    const dividerRow = this.createDividerRow();

    // Data row
    const dataRow = this.createTableRow([
      this.createTableCell(this.description, "task-desc"),
      this.createTableCell(this.formatPeriodicity(), "task-periodicity"),
      this.createTableCell(this.status, "task-status"),
      this.createCheckCircleCell(),
    ]);

    // Append rows to table
    table.append(headerRow, dividerRow, dataRow);

    // Append table to task card
    taskCard.appendChild(table);

    return taskCard;
  }
}
