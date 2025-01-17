import { WidgetService } from "./widgetService.js";

export class WidgetManager {
  constructor(userWidgets, importFunction = null) {
    this.userWidgets = userWidgets;
    this.renderedWidgets = new Set();
    this.importFunction = importFunction || ((path) => import(path));
    this.fetchedWidgets = new Set();
  }

  setupWidgetPlaceholders() {
    const container = document.getElementById("widget-container");
    this.userWidgets.forEach((widget) => {
      const placeholder = this.generatePlaceholderElement(widget.widgetName);
      container.appendChild(placeholder);
    });
  }

  generatePlaceholderElement(widgetName) {
    const placeholder = document.createElement("div");
    placeholder.className = "widget-placeholder";
    placeholder.dataset.widgetName = widgetName;
    placeholder.textContent = `Loading ${widgetName} widget...`;
    return placeholder;
  }

  async fetchAndPopulateWidget(widgetName, placeholder) {
    if (
      this.fetchedWidgets.has(widgetName) ||
      placeholder.dataset.loading === "true"
    ) {
      return;
    }

    this.fetchedWidgets.add(widgetName);
    placeholder.dataset.loading = "true";

    try {
      const fragmentContent = await WidgetService.fetchWidgetFragment(
        widgetName
      );
      placeholder.innerHTML = fragmentContent;
      await this.activateWidgetFunctionality(widgetName);
    } catch (error) {
      console.error(
        `Failed to fetch and populate widget ${widgetName}:`,
        error
      );
    } finally {
      delete placeholder.dataset.loading;
    }
  }

  async activateWidgetFunctionality(widgetName) {
    try {
      const module = await this.importFunction(
        `../widgets/${widgetName}Widget.js`
      );

      const WidgetClass = module.default || module.TaskWidget;

      if (WidgetClass && typeof WidgetClass === "function") {
        const widgetInstance = new WidgetClass();
        if (typeof widgetInstance.updateWidgetData === "function") {
          await widgetInstance.updateWidgetData();
        } else {
          console.log(
            `Widget ${widgetName} does not have an updateWidgetData method`
          );
        }
      } else {
        console.log(`No valid WidgetClass found for widget: ${widgetName}`);
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
      if (
        !this.renderedWidgets.has(widgetName) &&
        !this.fetchedWidgets.has(widgetName)
      ) {
        const placeholder = document.querySelector(
          `.widget-placeholder[data-widget-name="${widgetName}"]`
        );
        if (!placeholder) {
          const newPlaceholder = this.generatePlaceholderElement(widgetName);
          document
            .getElementById("widget-container")
            .appendChild(newPlaceholder);
          await this.fetchAndPopulateWidget(widgetName, newPlaceholder);
        } else {
          await this.fetchAndPopulateWidget(widgetName, placeholder);
        }
        this.renderedWidgets.add(widgetName);
      }
    }
  }
}
