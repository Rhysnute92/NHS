import { EventQueue } from "./eventQueue.js";

export class WorkerManager {
  constructor(workerUrl, apiUrl) {
    this.worker = new Worker(workerUrl);
    this.apiUrl = apiUrl;
    this.eventQueue = new EventQueue();
    this.initWorker();
    this.initEventQueueManagement();
  }

  initWorker() {
    console.log("Worker:", this.worker);
    this.worker.addEventListener("message", (event) => {
      if (event.data.status === "success") {
        console.log("Events synced successfully:", event.data.data);
      } else if (event.data.status === "error") {
        console.error("Failed to sync events:", event.data.error);
      }
    });
  }

  initEventQueueManagement() {
    window.addEventListener("beforeunload", () => {
      if (this.eventQueue.getSize() > 0) {
        localStorage.setItem(
          "pendingTasks",
          JSON.stringify(this.eventQueue.getEvents())
        );
      }
    });

    window.addEventListener("load", () => {
      const pendingTasks = JSON.parse(localStorage.getItem("pendingTasks"));
      if (pendingTasks) {
        this.eventQueue.queue = pendingTasks;
        this.worker.postMessage({ queue: pendingTasks });
        localStorage.removeItem("pendingTasks");
      }
    });

    setInterval(() => {
      if (this.eventQueue.getSize() > 0) {
        console.log(
          "Sending event queue to worker:",
          this.eventQueue.getEvents()
        );
        this.worker.postMessage({
          queue: this.eventQueue.getEvents(),
          apiUrl: this.apiUrl,
        });

        this.eventQueue.clearQueue();
      }
    }, 5000);
  }
}
