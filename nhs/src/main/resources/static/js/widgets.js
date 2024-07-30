document.addEventListener("DOMContentLoaded", function () {
  const userId = "current-user-id"; // Replace with the actual user ID
  fetch(`/api/user-widgets/${userId}`)
    .then((response) => response.json())
    .then((userWidgets) => {
      userWidgets.forEach((userWidget) => {
        fetch(`/api/widgets/${userWidget.widgetId}`)
          .then((response) => response.json())
          .then((widget) => {
            const widgetDiv = document.createElement("div");
            widgetDiv.innerHTML = widget.htmlContent;
            document.getElementById("widget-container").appendChild(widgetDiv);

            // Fetch data and update widget
            fetch(`/api/widgets/${userWidget.widgetId}/data`)
              .then((response) => response.json())
              .then((data) => {
                // Update widget content based on data
                const percentage =
                  (data.completedTasks / data.totalTasks) * 100;
                widgetDiv.querySelector(
                  ".percentage"
                ).innerText = `${percentage}%`;
              });
          });
      });
    });
});
