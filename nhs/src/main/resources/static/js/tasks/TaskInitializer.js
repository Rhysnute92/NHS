import { TaskManager } from "./taskManager.js";

export function initializeTaskManager(userID, eventQueue) {
  const taskManager = new TaskManager(userID, eventQueue);
  taskManager.fetchTasks();
}
