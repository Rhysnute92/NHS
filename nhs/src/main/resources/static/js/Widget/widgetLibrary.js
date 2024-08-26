import { WidgetService } from "./widgetService.js";
import { WidgetManager } from "./widgetManager.js";

document.addEventListener("DOMContentLoaded", async function () {
  const logId = Math.random().toString(36).substring(2, 10);
  const timestamp = new Date().toISOString();

  try {
    console.log(`[${timestamp}] [${logId}] Initializing widget library page`);
    const userWidgets = await WidgetService.fetchUserWidgets();
    console.log(`[${timestamp}] [${logId}] User widgets:`, userWidgets);

    // Render current user widgets on the dashboard
    renderWidgets(userWidgets, "current-widgets-grid");

    // Handle click events for widgets to indicate selection
    setupWidgetSelection();

    // Handle click event for the remove widget button
    document
      .querySelector(".remove-widget-button")
      .addEventListener("click", removeSelectedWidgets);
  } catch (error) {
    console.error(`[${timestamp}] [${logId}] Error occurred:`, error);
  }

  function renderWidgets(widgets, containerId) {
    const container = document.getElementById(containerId);
    container.innerHTML = ""; // Clear any existing content

    widgets.forEach((widget) => {
      const widgetItem = document.createElement("div");
      widgetItem.className = "widget-item";
      widgetItem.dataset.widgetName = widget.widgetName;

      const widgetIcon = document.createElement("div");
      widgetIcon.className = "widget-icon";
      widgetIcon.dataset.userWidgetid = widget.userWidgetID;

      const formattedName = formatWidgetName(widget.widgetName);
      const widgetName = document.createElement("p");
      widgetName.className = "widget-name";
      widgetName.textContent = formattedName;

      widgetItem.appendChild(widgetIcon);
      widgetItem.appendChild(widgetName);
      container.appendChild(widgetItem);

      // click event to select/deselect widget
      widgetItem.addEventListener("click", function () {
        widgetItem.classList.toggle("selected");
      });
    });
  }

  function setupWidgetSelection() {
    document.querySelectorAll(".widget-icon").forEach((item) => {
      item.addEventListener("click", function () {
        item.classList.toggle("selected");
      });
    });
  }

  function formatWidgetName(widgetName) {
    return widgetName
      .split("-")
      .map((word) => word.charAt(0).toUpperCase() + word.slice(1))
      .join(" ");
  }

  async function removeSelectedWidgets() {
    const selectedWidgets = document.querySelectorAll(".widget-icon.selected");
    const widgetIdsToRemove = Array.from(selectedWidgets).map(
      (widget) => widget.dataset.userwidgetid
    );
    console.log(
      `[${timestamp}] [${logId}]Selected widget IDs:`,
      widgetIdsToRemove
    );

    if (widgetIdsToRemove.length === 0) {
      alert("No widgets selected for removal.");
      return;
    }

    try {
      await WidgetService.removeUserWidgets(widgetIdsToRemove);
      console.log(
        `[${timestamp}] [${logId}] Selected widgets removed successfully.`
      );
      selectedWidgets.forEach((widget) => widget.remove()); // Remove from UI
    } catch (error) {
      console.error("Error removing widgets:", error);
      alert("Failed to remove selected widgets.");
    }
  }
});
