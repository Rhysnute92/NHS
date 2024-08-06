// widgetManager.js

import { WidgetService } from "./widgetService.js";

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
        const fragmentContent = await WidgetService.fetchWidgetFragment(
          widgetName
        );
        this.appendWidgetToDOM(widgetName, fragmentContent);
        this.addedWidgets.add(widgetName);
      } else {
        console.log(`Widget ${widgetName} already added. Skipping.`);
      }
    }
  }

  appendWidgetToDOM(widgetName, fragmentContent) {
    console.log("Fragment content:", fragmentContent);
    const fragment = document.createElement("div");
    fragment.innerHTML = fragmentContent;
    fragment.classList.add(`widget-${widgetName}`); // Add a unique class
    document.getElementById("widget-container").appendChild(fragment);
  }
}
