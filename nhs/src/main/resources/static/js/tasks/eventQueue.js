export class EventQueue {
  constructor() {
    this.queue = [];
  }

  addEvent(taskId, newStatus, metadata = {}) {
    this.queue.push({
      taskId,
      newStatus,
      timestamp: new Date().toISOString(),
      metadata,
    });
  }

  getEvents() {
    return this.queue;
  }

  clearQueue() {
    this.queue = [];
  }

  getSize() {
    return this.queue.length;
  }
}
