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
    return this.isTaskCompleteForDay(currentDay) ? "Complete" : "Incomplete";
  }

  // Method to check if the task is complete for a specific day
  isTaskCompleteForDay(day) {
    return (this.bitmask & (1 << (day - 1))) !== 0;
  }

  // Method to mark the task as complete for the current day
  markTaskComplete() {
    const currentDay = new Date().getDate();
    this.bitmask = this.bitmask | (1 << (currentDay - 1));
    this.status = "Complete";
  }

  // Method to mark the task as incomplete for the current day
  markTaskIncomplete() {
    const currentDay = new Date().getDate();
    this.bitmask = this.bitmask & ~(1 << (currentDay - 1));
    this.status = "Incomplete";
  }

  // Toggle the task's completion status for the current day
  toggleTaskCompletion() {
    if (this.status === "Complete") {
      this.markTaskIncomplete();
    } else {
      this.markTaskComplete();
    }
    console.log("Bitmask after toggling (binary):", this.bitmask.toString(2));
  }

  /**
   * Formats the periodicity to be title case.
   * @return {string} - The formatted periodicity.
   */
  formatPeriodicity() {
    return formatStringTitleCase(this.periodicity);
  }

  toUserTask() {
    const userTask = {
      id: this.id,
      task: {
        name: this.name,
        description: this.description,
        periodicity: this.periodicity,
      },
      bitmask: this.bitmask,
    };

    // Debugging: Log the constructed UserTask object
    console.log("Constructed UserTask:", userTask);
    console.log("UserTask bitmask (binary):", this.bitmask.toString(2));

    return userTask;
  }
}
