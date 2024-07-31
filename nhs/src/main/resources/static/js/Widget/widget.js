document.addEventListener("DOMContentLoaded", async function () {
  try {
    const userId = getUserId(); // Replace with your function to get the user ID
    console.log("User ID:", userId);

    const userWidgets = await fetch(`/api/user-widgets/${userId}`).then(
      (response) => {
        console.log("Fetched user widgets response:", response);
        return response.json();
      }
    );
    console.log("User widgets:", userWidgets);

    for (const userWidget of userWidgets) {
      const widgetName = userWidget.widgetName;
      console.log("Widget name:", widgetName);

      // Fetch the Thymeleaf-rendered widget content
      const fragmentContent = await fetch(`/api/widgets/${widgetName}`).then(
        (response) => {
          console.log(`Fetched widget (${widgetName}) response:`, response);
          if (!response.ok) {
            throw new Error("Widget not found");
          }
          return response.text();
        }
      );
      console.log("Fragment content:", fragmentContent);

      // Append the fetched content to the DOM
      const fragment = document.createElement("div");
      fragment.innerHTML = fragmentContent;
      document.getElementById("widget-container").appendChild(fragment);
    }
  } catch (error) {
    console.error("Error occurred:", error);
  }
});

// TODO: Replace the getUserId function with actual implementation
function getUserId() {
  console.log("Getting user ID");
  return "1";
}
