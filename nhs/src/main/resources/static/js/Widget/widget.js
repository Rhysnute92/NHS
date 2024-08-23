import { WidgetService } from "./widgetService.js";
import { WidgetManager } from "./widgetManager.js";

document.addEventListener("DOMContentLoaded", async function () {
  const logId = Math.random().toString(36).substring(2, 10);
  const timestamp = new Date().toISOString();

  try {
    console.log(`[${timestamp}] [${logId}] Initializing application`);
    const userWidgets = await WidgetService.fetchUserWidgets();
    console.log(`[${timestamp}] [${logId}] User widgets:`, userWidgets);

    const widgetManager = new WidgetManager(userWidgets);
    await widgetManager.setupWidgetPlaceholders();
    console.log(
      `[${timestamp}] [${logId}] Placeholders set up, rendering all widgets`
    );
    await widgetManager.renderAllUserWidgets();
  } catch (error) {
    console.error(`[${timestamp}] [${logId}] Error occurred:`, error);
  }
});
