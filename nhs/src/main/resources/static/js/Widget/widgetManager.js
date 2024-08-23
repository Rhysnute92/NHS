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
    const logId = Math.random().toString(36).substring(2, 10);
    const timestamp = new Date().toISOString();
    console.log(
      `[${timestamp}] [${logId}] Generating placeholder for widget: ${widgetName}`
    );

    const placeholder = document.createElement("div");
    placeholder.className = "widget-placeholder";
    placeholder.dataset.widgetName = widgetName;
    placeholder.textContent = `Loading ${widgetName} widget...`;

    console.log(
      `[${timestamp}] [${logId}] Placeholder generated:`,
      placeholder
    );
    return placeholder;
  }

  async fetchAndPopulateWidget(widgetName, placeholder) {
    const logId = Math.random().toString(36).substring(2, 10);
    const timestamp = new Date().toISOString();

    console.log(
      `[${timestamp}] [${logId}] Attempting to fetch widget: ${widgetName}`
    );

    if (
      this.fetchedWidgets.has(widgetName) ||
      placeholder.dataset.loading === "true"
    ) {
      console.log(
        `[${timestamp}] [${logId}] Widget ${widgetName} already fetched or loading. Skipping.`
      );
      return;
    }

    this.fetchedWidgets.add(widgetName); // Prevent future duplicate fetches
    placeholder.dataset.loading = "true"; // Mark as loading

    try {
      const fragmentContent = await WidgetService.fetchWidgetFragment(
        widgetName
      );
      console.log(
        `[${timestamp}] [${logId}] Fetched content for ${widgetName}:`,
        fragmentContent
      );
      placeholder.innerHTML = fragmentContent;
      await this.activateWidgetFunctionality(widgetName);
    } catch (error) {
      console.error(
        `[${timestamp}] [${logId}] Failed to fetch and populate widget ${widgetName}:`,
        error
      );
    } finally {
      delete placeholder.dataset.loading; // Remove loading state
      console.log(
        `[${timestamp}] [${logId}] Completed fetching for ${widgetName}`
      );
    }
  }

  async activateWidgetFunctionality(widgetName) {
    const logId = Math.random().toString(36).substring(2, 10);
    const timestamp = new Date().toISOString();

    try {
      console.log(
        `[${timestamp}] [${logId}] Activating functionality for widget: ${widgetName}`
      );

      // Import the module dynamically
      const module = await this.importFunction(
        `../widgets/${widgetName}Widget.js`
      );

      console.log("[${timestamp}] [${logId}] Module imported:", module);

      // Explicitly access the class
      const WidgetClass = module.default || module.TaskWidget;

      console.log("[${timestamp}] [${logId}] Widget class:", WidgetClass);

      if (WidgetClass && typeof WidgetClass === "function") {
        const widgetInstance = new WidgetClass();
        if (typeof widgetInstance.updateWidgetData === "function") {
          await widgetInstance.updateWidgetData();
        } else {
          console.log(
            `[${timestamp}] [${logId}] Widget ${widgetName} does not have an updateWidgetData method`
          );
        }
      } else {
        console.log(
          `[${timestamp}] [${logId}] No valid WidgetClass found for widget: ${widgetName}`
        );
      }
    } catch (error) {
      console.error(
        `[${timestamp}] [${logId}] Failed to activate functionality for widget ${widgetName}:`,
        error
      );
    }
  }

  async renderAllUserWidgets() {
    const logId = Math.random().toString(36).substring(2, 10);
    const timestamp = new Date().toISOString();

    console.log(`[${timestamp}] [${logId}] Rendering all user widgets`);
    for (const userWidget of this.userWidgets) {
      const widgetName = userWidget.widgetName;
      if (
        !this.renderedWidgets.has(widgetName) &&
        !this.fetchedWidgets.has(widgetName)
      ) {
        console.log(
          `[${timestamp}] [${logId}] Rendering widget: ${widgetName}`
        );
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
        console.log(`[${timestamp}] [${logId}] Rendered widget: ${widgetName}`);
      }
    }
  }
}
