import { WidgetService } from "./widgetService.js";
import { TaskWidget } from "../widgets/taskCompletionWidget.js";

export class WidgetManager {
  constructor(userWidgets) {
    this.userWidgets = userWidgets;
    this.addedWidgets = new Set();
    this.observer = new IntersectionObserver(
      this.handleIntersection.bind(this),
      {
        root: null,
        rootMargin: "0px",
        threshold: 0.1,
      }
    );
  }

  initializeUserWidgets() {
    const container = document.getElementById("widget-container");
    this.userWidgets.forEach((widget) => {
      const placeholder = this.createWidgetPlaceholder(widget.widgetName);
      container.appendChild(placeholder);
      this.observer.observe(placeholder);
    });
  }

  createWidgetPlaceholder(widgetName) {
    const placeholder = document.createElement("div");
    placeholder.className = "widget-placeholder";
    placeholder.dataset.widgetName = widgetName;
    placeholder.textContent = `Loading ${widgetName} widget...`;
    return placeholder;
  }

  async handleIntersection(entries, observer) {
    for (const entry of entries) {
      if (entry.isIntersecting) {
        const widgetName = entry.target.dataset.widgetName;
        await this.loadWidget(widgetName, entry.target);
        observer.unobserve(entry.target);
      }
    }
  }

  async loadWidget(widgetName, placeholder) {
    try {
      const fragmentContent = await WidgetService.fetchWidgetFragment(
        widgetName
      );
      placeholder.innerHTML = fragmentContent;
      this.initializeWidget(widgetName);
    } catch (error) {
      console.error(`Failed to load widget ${widgetName}:`, error);
    }
  }

  initializeWidget(widgetName) {
    switch (widgetName) {
      case "task-completion":
        const taskWidget = new TaskWidget();
        taskWidget.updateWidgetData();
        break;
      default:
        console.log(`No specific initialization for widget ${widgetName}`);
        break;
    }
  }

  async loadWidgets() {
    for (const userWidget of this.userWidgets) {
      const widgetName = userWidget.widgetName;
      if (!this.addedWidgets.has(widgetName)) {
        const placeholder = this.createWidgetPlaceholder(widgetName);
        document.getElementById("widget-container").appendChild(placeholder);
        await this.loadWidget(widgetName, placeholder);
        this.addedWidgets.add(widgetName);
      } else {
        console.log(`Widget ${widgetName} already added. Skipping.`);
      }
    }
  }
}
