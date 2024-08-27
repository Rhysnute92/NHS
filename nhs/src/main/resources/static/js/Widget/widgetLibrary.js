import { WidgetService } from "./widgetService.js";
import { WidgetManager } from "./widgetManager.js";

document.addEventListener("DOMContentLoaded", async function () {
  const logId = Math.random().toString(36).substring(2, 10);
  const timestamp = new Date().toISOString();

  try {
    console.log(`[${timestamp}] [${logId}] Initializing widget library page`);
    const userWidgets = await WidgetService.fetchUserWidgets();
    const availableWidgets = await WidgetService.fetchAvailableWidgets();
    console.log(`[${timestamp}] [${logId}] User widgets:`, userWidgets);
    console.log(
      `[${timestamp}] [${logId}] Available widgets:`,
      availableWidgets
    );

    // Render current user widgets on the dashboard
    renderWidgets(userWidgets, "current-widgets-grid");

    // Render available widgets not on the dashboard
    renderAvailableWidgets(availableWidgets, "available-widgets-grid");

    // Handle click events for widgets to indicate selection
    setupWidgetSelection();

    // Handle click event for the remove widget button
    document
      .querySelector(".remove-widget-button")
      .addEventListener("click", removeSelectedWidgets);

    // Handle click event for the add widget button
    document
      .querySelector(".add-widget-button")
      .addEventListener("click", addSelectedWidgets);
  } catch (error) {
    console.error(`[${timestamp}] [${logId}] Error occurred:`, error);
  }

  function renderWidgets(widgets, containerId) {
    const container = document.getElementById(containerId);
    container.innerHTML = ""; // Clear any existing content

    widgets.forEach((widget) => {
      console.log(`Rendering widget: ${JSON.stringify(widget)}`);
      const widgetItem = document.createElement("div");
      widgetItem.className = "widget-item";
      widgetItem.dataset.widgetName = widget.widgetName;

      const widgetIcon = document.createElement("div");
      widgetIcon.className = "widget-icon";
      widgetIcon.dataset.userwidgetid = widget.userWidgetID;
      console.log(`Assigned userWidgetID: ${widget.userWidgetID}`);

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

  function renderAvailableWidgets(widgetNames, containerId) {
    const container = document.getElementById(containerId);
    container.innerHTML = ""; // Clear any existing content

    widgetNames.forEach((widgetName) => {
      console.log(`Rendering available widget: ${widgetName}`);
      const widgetItem = document.createElement("div");
      widgetItem.className = "widget-item";
      widgetItem.dataset.widgetName = widgetName;

      const widgetIcon = document.createElement("div");
      widgetIcon.className = "widget-icon";
      widgetIcon.dataset.widgetname = widgetName;

      const formattedName = formatWidgetName(widgetName);
      const widgetNameElem = document.createElement("p");
      widgetNameElem.className = "widget-name";
      widgetNameElem.textContent = formattedName;

      widgetItem.appendChild(widgetIcon);
      widgetItem.appendChild(widgetNameElem);
      container.appendChild(widgetItem);

      // click event to select/deselect widget
      widgetItem.addEventListener("click", function () {
        widgetItem.classList.toggle("selected");
      });
    });
  }
  async function addSelectedWidgets() {
    const selectedWidgets = document.querySelectorAll(
      "#available-widgets-grid .widget-icon.selected"
    );
    const widgetNamesToAdd = Array.from(selectedWidgets).map((widget) => {
      return widget.dataset.widgetname; // Directly use the dataset attribute
    });

    console.log(
      `[${timestamp}] [${logId}] Selected widgets to add:`,
      widgetNamesToAdd
    );

    if (widgetNamesToAdd.includes(undefined) || widgetNamesToAdd.length === 0) {
      alert("No widgets selected for addition.");
      return;
    }

    try {
      await WidgetService.addUserWidgets(widgetNamesToAdd);
      console.log(
        `[${timestamp}] [${logId}] Selected widgets added successfully.`
      );
      // Refresh the lists after adding widgets
      const updatedUserWidgets = await WidgetService.fetchUserWidgets();
      const updatedAvailableWidgets =
        await WidgetService.fetchAvailableWidgets();
      renderWidgets(updatedUserWidgets, "current-widgets-grid");
      renderAvailableWidgets(updatedAvailableWidgets, "available-widgets-grid");
    } catch (error) {
      console.error("Error adding widgets:", error);
      alert("Failed to add selected widgets.");
    }
  }
  function setupWidgetSelection() {
    document.querySelectorAll(".widget-icon").forEach((item) => {
      item.addEventListener("click", function () {
        item.classList.toggle("selected");
        console.log(
          `Widget clicked: ${
            item.closest(".widget-item").dataset.widgetName
          }, Selected: ${item.classList.contains("selected")}`
        );
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
    const widgetIdsToRemove = Array.from(selectedWidgets).map((widget) => {
      if (widget.dataset.userwidgetid) {
        return widget.dataset.userwidgetid; // Directly use the dataset attribute
      } else {
        console.log(`Error: No widget ID found for selected widget:`, widget);
        return null;
      }
    });

    console.log(`[${timestamp}] [${logId}] Selected widgets:`, selectedWidgets);
    console.log(
      `[${timestamp}] [${logId}]Selected widget IDs:`,
      widgetIdsToRemove
    );

    if (widgetIdsToRemove.includes(null) || widgetIdsToRemove.length === 0) {
      alert("No valid widgets selected for removal.");
      return;
    }

    try {
      await WidgetService.removeUserWidgets(widgetIdsToRemove);
      console.log(
        `[${timestamp}] [${logId}] Selected widgets removed successfully.`
      );
      // Refresh the lists after removing widgets
      const updatedUserWidgets = await WidgetService.fetchUserWidgets();
      const updatedAvailableWidgets =
        await WidgetService.fetchAvailableWidgets();
      renderWidgets(updatedUserWidgets, "current-widgets-grid");
      renderAvailableWidgets(updatedAvailableWidgets, "available-widgets-grid");
    } catch (error) {
      console.error("Error removing widgets:", error);
      alert("Failed to remove selected widgets.");
    }
  }
});
