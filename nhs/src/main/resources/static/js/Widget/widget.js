// widget.js

import { WidgetService } from "./widgetService.js";
import { WidgetManager } from "./widgetManager.js";
import {  fetchUserID } from "../common/utils/accountUtility.js";

document.addEventListener("DOMContentLoaded", async function () {
  try {
    const userId = await fetchUserID();
    console.log("User ID:", userId);

    const userWidgets = await WidgetService.fetchUserWidgets(userId);
    console.log("User widgets:", userWidgets);

    const widgetManager = new WidgetManager(userWidgets);
    await widgetManager.loadWidgets();
  } catch (error) {
    console.error("Error occurred:", error);
  }
});