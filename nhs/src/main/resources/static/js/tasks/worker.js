self.addEventListener("message", async function (event) {
  console.log("Received event in worker:", event.data);

  const { queue } = event.data;

  try {
    const responses = await Promise.all(
      queue.map(async (eventItem) => {
        const { taskId } = eventItem;
        console.log("Processing task with ID:", taskId);

        if (!taskId) {
          throw new Error("taskId is undefined or null");
        }
        
        const response = await fetch(`/usertask/task-complete/${taskId}`, {
          method: "PUT",
          headers: {
            "Content-Type": "application/json",
          },
        });

        if (!response.ok) {
          throw new Error(
            `Failed to mark task ${taskId} as completed: ${response.statusText}`
          );
        }
        return response.json();
      })
    );

    console.log("API Responses:", responses);
    self.postMessage({ status: "success", data: responses });
  } catch (error) {
    console.error("Worker failed to process events:", error);
    self.postMessage({ status: "error", error: error.message });
  }
});
