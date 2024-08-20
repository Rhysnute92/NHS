import { ChartRenderer } from "../Questionnaire/ChartRenderer.js";
import { initializeCollapsible } from "./collapsible.js";
import { initializeQuestionnaireManager } from "../questionnaire/QuestionaireInitializer.js";
import { initializeTaskManager } from "../tasks/TaskInitializer.js";
import { WorkerManager } from "../tasks/WorkerManager.js";
import { fetchUserID } from "../common/utils/accountUtility.js";
import { TaskManager } from "../tasks/taskManager.js";
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
  const taskManager = new TaskManager(eventQueue);
  const mainPageTaskContainer = document.querySelector(
    ".my-health-tasks .tasks-container"
  );

  const questionnaireContainerId = "questionnaire-container";
  const noAssignmentContainerId = "no-assignment";

  // Initialize the questionnaireManager
  const questionnaireManager = await initializeQuestionnaireManager(
    questionnaireContainerId,
    noAssignmentContainerId
  );

  if (questionnaireManager) {
    await questionnaireManager.loadAssignedQuestionnaires();
    await questionnaireManager.loadAndRenderTrendData();
  }

  taskManager
    .fetchTasks()
    .then(() => {
      taskManager.renderTasks(mainPageTaskContainer); // Pass the container
    })
    .catch((error) => {
      taskManager.displayTaskErrorMessage(mainPageTaskContainer); // Pass the container to display error
    });

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
  }, 1000);

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
