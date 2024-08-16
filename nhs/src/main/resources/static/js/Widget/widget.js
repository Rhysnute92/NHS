// widget.js

import { WidgetService } from "./widgetService.js";
import { WidgetManager } from "./widgetManager.js";
import { getUserId } from "../common/utils/userUtility.js";

document.addEventListener("DOMContentLoaded", async function () {
  try {
    const userId = getUserId();
    console.log("User ID:", userId);

    const userWidgets = await WidgetService.fetchUserWidgets(userId);
    console.log("User widgets:", userWidgets);

    const widgetManager = new WidgetManager(userWidgets);
    await widgetManager.loadWidgets();
  } catch (error) {
    console.error("Error occurred:", error);
  }
});
