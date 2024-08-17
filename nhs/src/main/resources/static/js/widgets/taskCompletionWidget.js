export class TaskWidget {
  constructor() {
    // Ensure the correct widget element is selected
    this.widgetElement = document.querySelector(".widget-task-completion");
    if (!this.widgetElement) {
      console.error("Task widget element not found.");
      return;
    }
    console.log("Widget Element:", this.widgetElement);
  }

  async updateWidgetData() {
    // Prevent function from running if the widget element is not found
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
    // Double-check that the widget element is still available
    if (!this.widgetElement) {
      console.error("Task widget element not found during update.");
      return;
    }

    console.log("Updating progress circle with", completedTasks, totalTasks);

    // Safeguards to ensure the elements are found before interacting with them
    const progressCircle = this.widgetElement.querySelector(
      ".progress-circle .progress-circle"
    );
    const progressText = this.widgetElement.querySelector("#completedTasks");
    const totalText = this.widgetElement.querySelector("#totalTasks");

    if (!progressCircle || !progressText || !totalText) {
      console.error("Progress elements not found.");
      return;
    }

    const dashArray = 282.6;
    const dashOffset = dashArray - dashArray * (completedTasks / totalTasks);

    console.log("Setting dashOffset to", dashOffset);
    progressCircle.style.strokeDashoffset = dashOffset;
    progressText.textContent = completedTasks;
    totalText.textContent = totalTasks;
  }
}
