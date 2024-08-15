import { Task } from "./task.js"; // Import the Task class
import { TaskRenderer } from "./taskRenderer.js"; // Import the TaskRenderer class
import { fetchData } from "../common/utils/apiUtility.js"; // Utility for API calls

export class TaskManager {
  constructor(userId, eventQueue) {
    this.userId = userId;
    this.tasks = [];
    this.taskRenderer = new TaskRenderer(eventQueue, userId);
    this.eventQueue = eventQueue;
  }

  async fetchTasks() {
    try {
      console.debug(`Fetching tasks for user ID: ${this.userId}`);
      const taskData = await fetchData(`/usertask/user/${this.userId}`);
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
      console.debug("Fetched tasks: ", this.tasks);
      this.renderTasks();
    } catch (error) {
      console.error("Error fetching tasks:", error);
      this.displayTaskErrorMessage();
    }
  }

  renderTasks() {
    const tasksContainer = this.getTasksContainer();
    this.clearContainer(tasksContainer);
    console.debug("Rendering tasks...");
    this.tasks.forEach((task) => {
      tasksContainer.appendChild(this.taskRenderer.renderTaskCard(task));
    });
  }

  displayTaskErrorMessage() {
    const tasksContainer = this.getTasksContainer();
    this.clearContainer(tasksContainer);

    const errorMessage = document.createElement("p");
    errorMessage.classList.add("error-message");
    errorMessage.textContent = "Unable to load tasks. Please try again later.";
    tasksContainer.appendChild(errorMessage);
    console.debug("Displayed task error message.");
  }

  getTasksContainer() {
    return document.querySelector(".my-health-tasks .tasks-container");
  }

  clearContainer(container) {
    container.innerHTML = "";
    console.debug("Cleared tasks container.");
  }
}
