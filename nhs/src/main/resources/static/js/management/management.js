import { initializeCollapsible } from "./collapsible.js";
import { TaskManager } from "../tasks/taskManager.js";
import { getUserId } from "../common/utils/userUtility.js";
import { EventQueue } from "../tasks/eventQueue.js";

const worker = new Worker("/js/tasks/worker.js");
console.log("Worker:", worker);
const eventQueue = new EventQueue();
const apiUrl = "/usertask/task-update/batch";

document.addEventListener("DOMContentLoaded", function () {
  initializeCollapsible();
  const taskManager = new TaskManager(getUserId(), eventQueue);
  taskManager.fetchTasks();

  // Periodically send the event queue to the Web Worker for processing
  setInterval(() => {
    if (eventQueue.getSize() > 0) {
      console.log("Sending event queue to worker:", eventQueue.getEvents());
      worker.postMessage({
        queue: eventQueue.getEvents(),
        apiUrl: apiUrl,
      });

      // Clear the queue after sending it to the worker
      eventQueue.clearQueue();
    }
  }, 5000);

  // Handle messages from the Web Worker
  worker.addEventListener(
    "message",
    function (event) {
      if (event.data.status === "success") {
        console.log("Events synced successfully:", event.data.data);
      } else if (event.data.status === "error") {
        console.error("Failed to sync events:", event.data.error);
      }
    },
    false
  );
});
