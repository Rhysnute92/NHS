import { WidgetService } from "./widgetService.js";
import { WidgetManager } from "./widgetManager.js";

document.addEventListener("DOMContentLoaded", async function () {
  try {
    const userWidgets = await WidgetService.fetchUserWidgets();
    console.log("User widgets:", userWidgets);

    const widgetManager = new WidgetManager(userWidgets);
    await widgetManager.loadWidgets();
  } catch (error) {
    console.error("Error occurred:", error);
  }
});
