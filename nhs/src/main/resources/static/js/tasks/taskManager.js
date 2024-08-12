import { Task } from "./task.js";

export class TaskManager {
  constructor(userId) {
    this.userId = userId;
    this.tasks = [];
  }

  /**
   * Fetches tasks from the API and updates the internal tasks array.
   *
   * @return {Promise<void>} A Promise that resolves when the tasks are fetched and displayed.
   * @throws {Error} If there is an error fetching the tasks.
   */
  async fetchTasks() {
    try {
      const response = await fetch(`/usertask/user/${this.userId}`);
      if (!response.ok) {
        throw new Error("Failed to fetch tasks");
      }
      const taskData = await response.json();
      this.tasks = taskData.map(
        (userTask) =>
          new Task(
            userTask.id,
            userTask.task.name,
            userTask.task.description,
            userTask.bitmask,
            userTask.task.periodicity
          )
      );

      this.renderTasks();
    } catch (error) {
      console.error("Error fetching tasks:", error);
      this.displayTaskErrorMessage();
    }
  }

  /**
   * Renders the tasks in the tasks container.
   *
   * @return {void} This function does not return anything.
   */
  renderTasks() {
    const tasksContainer = this.getTasksContainer();
    this.clearContainer(tasksContainer);

    this.tasks.forEach((task) => {
      tasksContainer.appendChild(task.renderTaskCard());
    });
  }

  /**
   * Displays an error message in the tasks container.
   *
   * @return {void} No return value.
   */
  displayTaskErrorMessage() {
    const tasksContainer = this.getTasksContainer();
    this.clearContainer(tasksContainer);

    const errorMessage = document.createElement("p");
    errorMessage.classList.add("error-message");
    errorMessage.textContent = "Unable to load tasks. Please try again later.";
    tasksContainer.appendChild(errorMessage);
  }

  /**
   * Gets the container element for the tasks.
   *
   * @return {HTMLElement} - The container element where tasks will be rendered.
   */
  getTasksContainer() {
    return document.querySelector(".my-health-tasks .collapsible-content");
  }

  /**
   * Clears all child elements from a container.
   *
   * @param {HTMLElement} container - The container to clear.
   * @return {void} No return value.
   */
  clearContainer(container) {
    container.innerHTML = "";
  }
}
