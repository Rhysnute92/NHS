import { initializeCollapsible } from "./collapsible.js";
import { QuestionnaireManager } from "../questionnaire/QuestionnaireManager.js";
import { fetchUserID } from "../common/utils/accountUtility.js";
import { TaskManager } from "../tasks/taskManager.js";
import { getUserId } from "../common/utils/userUtility.js";
import { EventQueue } from "../tasks/eventQueue.js";

const worker = new Worker("/js/tasks/worker.js");
console.log("Worker:", worker);
const eventQueue = new EventQueue();
const apiUrl = "/usertask/task-update/batch";

// Store pending tasks in localStorage before unloading the page
window.addEventListener("beforeunload", () => {
  if (eventQueue.getSize() > 0) {
    localStorage.setItem(
      "pendingTasks",
      JSON.stringify(eventQueue.getEvents())
    );
  }
});

// Load pending tasks from localStorage when the page loads
window.addEventListener("load", () => {
  const pendingTasks = JSON.parse(localStorage.getItem("pendingTasks"));
  if (pendingTasks) {
    eventQueue.queue = pendingTasks;
    worker.postMessage({ queue: pendingTasks });
    localStorage.removeItem("pendingTasks");
  }
});

document.addEventListener("DOMContentLoaded", async function () {
  initializeCollapsible();

  try {
    // Fetch the user ID dynamically
    const userID = await fetchUserID();

    // Check if userID is valid
    if (!userID) {
      console.error("Failed to fetch user ID");
      return;
    }

    // Instantiate the QuestionnaireManager
    const questionnaireManager = new QuestionnaireManager(
      "questionnaire-container",
      "no-assignment",
      userID
    );

    // Load assigned questionnaires
    await questionnaireManager.loadAssignedQuestionnaires();
  } catch (error) {
    console.error("Error initializing questionnaire manager:", error);
  }
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
