export class TaskWidget {
  constructor() {
    this.widgetElement = document.querySelector(".widget-task-completion");
    if (!this.widgetElement) {
      console.error("Task widget element not found.");
      return;
    }
    console.log("Widget Element:", this.widgetElement);
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
}
