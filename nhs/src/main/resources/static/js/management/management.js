import { initializeCollapsible } from "./collapsible.js";
import { TaskManager } from "../tasks/taskManager.js";
import { getUserId } from "../common/utils/userUtility.js";

document.addEventListener("DOMContentLoaded", function () {
  initializeCollapsible();
  const taskManager = new TaskManager(getUserId());
  taskManager.fetchTasks();
});
