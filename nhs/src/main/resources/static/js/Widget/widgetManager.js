import { WidgetService } from "./widgetService.js";

export class WidgetManager {
  constructor(userWidgets, importFunction = null) {
    this.userWidgets = userWidgets;
    this.renderedWidgets = new Set();
    this.importFunction = importFunction || ((path) => import(path));
    this.observer = new IntersectionObserver(
      this.handleWidgetVisibility.bind(this),
      {
        root: null,
        rootMargin: "0px",
        threshold: 0.1,
      }
    );
    this.fetchedWidgets = new Set();
  }

  setupWidgetPlaceholders() {
    const container = document.getElementById("widget-container");
    this.userWidgets.forEach((widget) => {
      const placeholder = this.generatePlaceholderElement(widget.widgetName);
      container.appendChild(placeholder);
      this.observer.observe(placeholder);
    });
  }

  generatePlaceholderElement(widgetName) {
    const placeholder = document.createElement("div");
    placeholder.className = "widget-placeholder";
    placeholder.dataset.widgetName = widgetName;
    placeholder.textContent = `Loading ${widgetName} widget...`;
    return placeholder;
  }

  async handleWidgetVisibility(entries, observer) {
    for (const entry of entries) {
      if (entry.isIntersecting) {
        const widgetName = entry.target.dataset.widgetName;
        await this.fetchAndPopulateWidget(widgetName, entry.target);
        observer.unobserve(entry.target);
      }
    }
  }

  async fetchAndPopulateWidget(widgetName, placeholder) {
    if (this.fetchedWidgets.has(widgetName)) {
      return;
    }

    try {
      const fragmentContent = await WidgetService.fetchWidgetFragment(
        widgetName
      );
      placeholder.innerHTML = fragmentContent;
      this.fetchedWidgets.add(widgetName);

      await this.activateWidgetFunctionality(widgetName);
    } catch (error) {
      console.error(
        `Failed to fetch and populate widget ${widgetName}:`,
        error
      );
    }
  }

  async activateWidgetFunctionality(widgetName) {
    try {
      const module = await this.importFunction(
        `../widgets/${widgetName}Widget.js`
      );
      const WidgetClass = module.default || module[`${widgetName}Widget`];
      if (WidgetClass && typeof WidgetClass === "function") {
        const widgetInstance = new WidgetClass();
        if (typeof widgetInstance.updateWidgetData === "function") {
          await widgetInstance.updateWidgetData();
        }
      }
    } catch (error) {
      console.error(
        `Failed to activate functionality for widget ${widgetName}:`,
        error
      );
    }
  }

  async renderAllUserWidgets() {
    for (const userWidget of this.userWidgets) {
      const widgetName = userWidget.widgetName;

      if (!this.renderedWidgets.has(widgetName)) {
        const placeholder = this.generatePlaceholderElement(widgetName);
        document.getElementById("widget-container").appendChild(placeholder);

        await this.fetchAndPopulateWidget(widgetName, placeholder);
        this.renderedWidgets.add(widgetName);
      }
    }
  }
}
