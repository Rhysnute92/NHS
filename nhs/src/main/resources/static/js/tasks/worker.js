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

        const response = await fetch(`/usertask/task-toggle/${taskId}`, {
          method: "PUT",
          headers: {
            "Content-Type": "application/json",
          },
        });

        // Handle empty responses
        const textResponse = await response.text();
        if (!textResponse) {
          console.warn(`Empty response for task ${taskId}`);
          return { taskId, status: "no-content" };
        }

        try {
          return JSON.parse(textResponse);
        } catch (error) {
          throw new Error(
            `Failed to parse JSON response for task ${taskId}: ${error.message}`
          );
        }
      })
    );

    console.log("API Responses:", responses);
    self.postMessage({ status: "success", data: responses });
  } catch (error) {
    console.error("Worker failed to process events:", error);
    self.postMessage({ status: "error", error: error.message });
  }
});
