import { TaskRenderer } from "../tasks/taskRenderer.js";
import { fetchData } from "../common/utils/apiUtility.js"; // Utility for API calls

export class TaskWidget {
  constructor() {
    this.widgetElement = document.querySelector(".widget-task-completion");
    if (!this.widgetElement) {
      console.error("Task widget element not found.");
      return;
    }
    console.log("Widget Element:", this.widgetElement);

    this.taskRenderer = new TaskRenderer(null, null); // No eventQueue or userId needed for popup tasks
  }

  async updateWidgetData() {
    if (!this.widgetElement) return;

    try {
      const response = await fetch("/api/widgets/task-completion/data");
      if (response.ok) {
        const data = await response.json();
        console.log("API data:", data);
        this.updateProgressCircle(data.completedTasks, data.totalTasks);
      } else {
        console.error("Failed to fetch task data");
      }
    } catch (error) {
      console.error("Error fetching task data:", error);
    }
  }

  updateProgressCircle(completedTasks, totalTasks) {
    if (!this.widgetElement) {
      console.error("Task widget element not found during update.");
      return;
    }

    console.log("Updating progress circle with", completedTasks, totalTasks);

    const progressCircle = this.widgetElement.querySelector(
      ".progress-circle .progress-circle"
    );
    const progressText = this.widgetElement.querySelector("#completedTasks");
    const totalText = this.widgetElement.querySelector("#totalTasks");

    if (!progressCircle || !progressText || !totalText) {
      console.error("Progress elements not found.");
      return;
    }

    const radius = 90;
    const circumference = 2 * Math.PI * radius;
    const dashOffset =
      circumference - (circumference * completedTasks) / totalTasks;

    // Apply the stroke color (NHS aqua green)
    progressCircle.style.stroke = "var(--nhs-aqua-green)";

    // Apply the styles
    progressCircle.style.strokeDasharray = circumference;
    progressCircle.style.strokeDashoffset = dashOffset;

    // Update the text content
    progressText.textContent = completedTasks;
    totalText.textContent = totalTasks;
  }

  async initializePopup() {
    const completeTaskButton = this.widgetElement.querySelector(
      ".complete-task-button"
    );
    const taskPopup = this.widgetElement.querySelector("#taskPopup");
    const taskPopupOverlay =
      this.widgetElement.querySelector("#taskPopupOverlay");
    const closePopupButton = taskPopup.querySelector("#closePopupButton");
    const taskListContainer = taskPopup.querySelector("#taskListContainer");

    if (
      !completeTaskButton ||
      !taskPopup ||
      !closePopupButton ||
      !taskListContainer
    ) {
      console.error("Popup elements not found.");
      return;
    }

    // Show the popup and load tasks
    completeTaskButton.addEventListener("click", async () => {
      taskPopupOverlay.style.display = "block"; // Show overlay
      taskPopup.style.display = "block"; // Show popup

      // Fetch and render tasks
      await this.fetchAndRenderTasks(taskListContainer);
    });

    // Hide the popup and overlay when the close button is clicked or overlay is clicked
    const closePopup = () => {
      taskPopupOverlay.style.display = "none"; // Hide overlay
      taskPopup.style.display = "none"; // Hide popup
    };

    closePopupButton.addEventListener("click", closePopup);
    taskPopupOverlay.addEventListener("click", closePopup);
  }

  async fetchAndRenderTasks(taskListContainer) {
    // Fetch tasks from the API
    try {
      const response = await fetch("/usertask/user/1"); // Replace with the correct user ID or dynamic value
      if (response.ok) {
        const tasks = await response.json();
        this.taskRenderer.renderTaskWidgetTasks(tasks, taskListContainer);
      } else {
        console.error("Failed to fetch tasks for the popup");
      }
    } catch (error) {
      console.error("Error fetching tasks for the popup:", error);
    }
  }
}
