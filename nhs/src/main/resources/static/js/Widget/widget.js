document.addEventListener("DOMContentLoaded", async function () {
  try {
    console.log("Page loaded");
    const userId = getUserId(); // replace with your function to get the user ID
    console.log("User ID:", userId);
    /* const userWidgets = await fetch(`/api/user-widgets/${userId}`).then(
      (response) => response.json()
    );
    console.log("User widgets:", userWidgets);
    for (const userWidget of userWidgets) {
      const widgetName = userWidget.widgetName;
      console.log("Widget name:", widgetName);
      const fragmentName = await fetch(`/api/widgets/${widgetName}`).then(
        (response) => response.text()
      );
      console.log("Fragment name:", fragmentName);
      const fragmentContent = await fetch(`/widgets/${fragmentName}`).then(
        (response) => response.text()
      );
      console.log("Fragment content:", fragmentContent);
      const fragment = document.createElement("template");
      fragment.innerHTML = fragmentContent;
      console.log("Fragment:", fragment);
      document.getElementById("widget-container").appendChild(fragment.content);
    } */
  } catch (error) {
    console.error(error);
  }
});

// TODO: Replace the getUserId function with actualimplementation
function getUserId() {
  console.log("Getting user ID");
  return "1";
}
