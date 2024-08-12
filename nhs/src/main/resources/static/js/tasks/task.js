export class Task {
  constructor(id, name, description, bitmask) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.bitmask = bitmask;
    this.status = this.calculateStatus();
  }

  // Method to check the bit for the current day and set the status
  calculateStatus() {
    const currentDay = new Date().getDate();
    const isBitSet = (this.bitmask & (1 << (currentDay - 1))) !== 0;
    return isBitSet ? "Complete" : "Incomplete";
  }

/**
 * Renders a task item as an HTML element with the task's name, description, and status.
 *
 * @return {HTMLElement} The task item element.
 */
  renderTaskItem() {
    const taskItem = document.createElement("li");
    taskItem.classList.add("task-item");

    const taskName = document.createElement("h4");
    taskName.classList.add("task-name");
    taskName.textContent = this.name;

    const taskDesc = document.createElement("p");
    taskDesc.classList.add("task-desc");
    taskDesc.textContent = this.description;

    const taskStatus = document.createElement("p");
    taskStatus.classList.add("task-status");
    taskStatus.textContent = `Status: ${this.status}`;

    taskItem.appendChild(taskName);
    taskItem.appendChild(taskDesc);
    taskItem.appendChild(taskStatus);

    return taskItem;
  }
}
