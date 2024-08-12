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

  createCheckCircle() {
    const checkCircleWrapper = document.createElement("div");
    checkCircleWrapper.classList.add("check-circle-wrapper");

    const checkmarkSVG = `
      <svg class="checkmark" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 52 52">
        <circle class="checkmark__circle" cx="26" cy="26" r="25" fill="none"/>
        <path class="checkmark__check" fill="none" d="M14.1 27.2l7.1 7.2 16.7-16.8"/>
      </svg>
    `;

    checkCircleWrapper.innerHTML = checkmarkSVG;
  }

  /**
   * Renders a task item as an HTML element with the task's name, description, and status.
   *
   * @return {HTMLElement} The task item element.
   */
  renderTaskItem() {
    const taskCard = document.createElement("div");
    taskItem.classList.add("task-card");

    const taskName = document.createElement("h3");
    taskName.classList.add("task-name");
    taskName.textContent = this.name;

    const taskDesc = document.createElement("p");
    taskDesc.classList.add("task-desc");
    taskDesc.textContent = this.description;

    const taskMeta = document.createElement("div");
    taskStatus.classList.add("task-status");
    taskStatus.textContent = `Status: ${this.status}`;

    const taskPeriodicity = document.createElement("span");
    taskPeriodicity.classList.add("task-periodicity");
    taskPeriodicity.textContent = `Periodicity: ${this.periodicity}`;

    taskItem.appendChild(taskName);
    taskItem.appendChild(taskDesc);
    taskItem.appendChild(taskStatus);
    taskItem.appendChild(taskPeriodicity);

    return taskItem;
  }
}
