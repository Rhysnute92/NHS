import { Task } from "./task.js";
import { TaskRenderer } from "./taskRenderer.js";
import { fetchData } from "../common/utils/apiUtility.js";

export class TaskManager {
  constructor(eventQueue, taskRenderer) {
    this.tasks = [];
    this.taskRenderer = taskRenderer || new TaskRenderer(eventQueue); // Allow injection of a TaskRenderer instance
    this.eventQueue = eventQueue;
  }

  async fetchTasks() {
    try {
      const taskData = await fetchData(`/usertask/user`);
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
      return this.tasks; // Return tasks so they can be used externally if needed
    } catch (error) {
      console.error("Error fetching tasks:", error);
      throw error; // Re-throw the error to handle it externally
    }
  }

  renderTasks(container) {
    this.clearContainer(container);
    console.debug("Rendering tasks...");
    this.tasks.forEach((task) => {
      container.appendChild(this.taskRenderer.renderTaskCard(task));
    });
  }

  displayTaskErrorMessage(container) {
    this.clearContainer(container);

    const errorMessage = document.createElement("p");
    errorMessage.classList.add("error-message");
    errorMessage.textContent = "Unable to load tasks. Please try again later.";
    container.appendChild(errorMessage);
    console.debug("Displayed task error message.");
  }

  clearContainer(container) {
    if (container) {
      container.innerHTML = "";
      console.debug("Cleared tasks container.");
    } else {
      console.error("No container provided to clear.");
    }
  }
}
