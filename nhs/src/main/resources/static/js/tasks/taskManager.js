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
        (task) =>
          new Task(task.id, task.task.name, task.task.description, task.bitmask)
      );
      this.displayTasks();
    } catch (error) {
      console.error("Error fetching tasks:", error);
      this.displayErrorMessage();
    }
  }

/**
 * Displays the tasks in the tasks container.
 *
 * @return {void} This function does not return anything.
 */
  displayTasks() {
    const tasksContainer = document.querySelector(
      ".my-health-tasks .collapsible-content"
    );
    const tasksList = document.createElement("ul");
    tasksList.classList.add("task-list");

    this.tasks.forEach((task) => {
      tasksList.appendChild(task.renderTaskItem());
    });

    tasksContainer.appendChild(tasksList);
  }

/**
 * Displays an error message in the tasks container.
 *
 * @return {void} No return value.
 */
  displayErrorMessage() {
    const tasksContainer = document.querySelector(
      ".my-health-tasks .collapsible-content"
    );
    const errorMessage = document.createElement("p");
    errorMessage.classList.add("error-message");
    errorMessage.textContent = "Unable to load tasks. Please try again later.";
    tasksContainer.appendChild(errorMessage);
  }
}
