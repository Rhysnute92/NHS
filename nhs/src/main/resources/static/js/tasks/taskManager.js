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
      tasksContainer.appendChild(this.renderTaskCard(task));
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
   * Renders a task as a card element.
   *
   * @param {Task} task - The task object to render.
   * @return {HTMLElement} - The rendered task card.
   */
  renderTaskCard(task) {
    const taskCard = document.createElement("div");
    taskCard.classList.add("task-card");

    const taskName = document.createElement("h3");
    taskName.classList.add("task-name");
    taskName.textContent = task.name;

    const taskDesc = document.createElement("p");
    taskDesc.classList.add("task-desc");
    taskDesc.textContent = task.description;

    const taskStatus = document.createElement("p");
    taskStatus.classList.add("task-status");
    taskStatus.textContent = `Status: ${task.status}`;

    const taskPeriodicity = document.createElement("p");
    taskPeriodicity.classList.add("task-periodicity");
    taskPeriodicity.textContent = `Periodicity: ${task.periodicity}`;

    taskCard.append(taskName, taskDesc, taskStatus, taskPeriodicity);
    return taskCard;
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
