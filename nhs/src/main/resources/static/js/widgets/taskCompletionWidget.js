import { TaskRenderer } from "../tasks/taskRenderer.js";
import { TaskManager } from "../tasks/taskManager.js";
import { EventQueue } from "../tasks/eventQueue.js";

export class TaskWidget {
  constructor() {
    this.widgetElement = document.querySelector(".widget-task-completion");
    if (!this.widgetElement) {
      console.error("Task widget element not found.");
      return;
    }
    console.log("Widget Element:", this.widgetElement);

    // Initialize EventQueue and TaskManager with a TaskRenderer
    this.eventQueue = new EventQueue();
    this.taskRenderer = new TaskRenderer(this.eventQueue, null);
    this.taskManager = new TaskManager(this.eventQueue, this.taskRenderer);

    this.worker = new Worker("/js/tasks/worker.js"); // Web Worker for async task updates
    this.initializePopup();

    // Periodically send the event queue to the Web Worker for processing
    setInterval(() => {
      if (this.eventQueue.getSize() > 0) {
        console.log("Sending event queue to worker:", this.eventQueue.getEvents());
        this.worker.postMessage({
          queue: this.eventQueue.getEvents(),
          apiUrl: "/usertask/task-update/batch",
        });

        // Clear the queue after sending it to the worker
        this.eventQueue.clearQueue();
      }
    }, 1000);

    // Handle messages from the Web Worker
    this.worker.addEventListener(
      "message",
      function (event) {
        if (event.data.status === "success") {
          console.log("Events synced successfully:", event.data.data);
        } else if (event.data.status === "error") {
          console.error("Failed to sync events:", event.data.error);
        }
      },
      false
    );
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

    // Apply the stroke color 
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

    // Ensure the popup is hidden initially
    taskPopupOverlay.style.display = "none";
    taskPopup.style.display = "none";

    console.log("Popup hidden on initialization");

    // Show the popup and load tasks
    completeTaskButton.addEventListener("click", async () => {
      console.log("Complete task button clicked");
      taskPopupOverlay.style.display = "block"; // Show overlay
      taskPopup.style.display = "block"; // Show popup

      // Fetch and render tasks
      await this.fetchAndRenderTasks(taskListContainer);
    });

    // Hide the popup and overlay when the close button or overlay is clicked
    const closePopup = () => {
      console.log("Close popup triggered");
      taskPopupOverlay.style.display = "none"; // Hide overlay
      taskPopup.style.display = "none"; // Hide popup
    };

    closePopupButton.addEventListener("click", closePopup);
    taskPopupOverlay.addEventListener("click", closePopup);
  }

  async fetchAndRenderTasks(taskListContainer) {
    try {
      await this.taskManager.fetchTasks();
      this.taskRenderer.renderTaskPopup(this.taskManager.tasks, taskListContainer); // Pass the specific container for rendering
    } catch (error) {
      console.error("Error fetching and rendering tasks:", error);
      this.taskManager.displayTaskErrorMessage(taskListContainer);
    }
  }
}
