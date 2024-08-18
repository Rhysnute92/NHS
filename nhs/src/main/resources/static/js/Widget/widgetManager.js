import { WidgetService } from "./widgetService.js";
import { TaskWidget } from "../widgets/taskCompletionWidget.js";

export class WidgetManager {
  constructor(userWidgets) {
    this.userWidgets = userWidgets;
    this.addedWidgets = new Set();
  }

  async loadWidgets() {
    for (const userWidget of this.userWidgets) {
      const widgetName = userWidget.widgetName;
      console.log("Widget name:", widgetName);

      if (!this.addedWidgets.has(widgetName)) {
        const fragmentContent = await WidgetService.fetchWidgetFragment(widgetName);
        this.appendWidgetToDOM(widgetName, fragmentContent);
        this.addedWidgets.add(widgetName);

        this.initializeWidget(widgetName); // Initialize the widget after adding it to DOM
      } else {
        console.log(`Widget ${widgetName} already added. Skipping.`);
      }
    }
  }

  appendWidgetToDOM(widgetName, fragmentContent) {
    const fragment = document.createElement("div");
    fragment.innerHTML = fragmentContent;
    fragment.classList.add(`widget-${widgetName}`); // Add a unique class
    document.getElementById("widget-container").appendChild(fragment);
  }

  initializeWidget(widgetName) {
    // Dynamically initialize the widget based on its name
    switch (widgetName) {
      case "task-completion":
        const taskWidget = new TaskWidget();
        taskWidget.updateWidgetData(); // Update widget data immediately
        break;

      // Add initialization for more widgets here if needed

      default:
        console.log(`No specific initialization for widget ${widgetName}`);
        break;
    }
  }
}
