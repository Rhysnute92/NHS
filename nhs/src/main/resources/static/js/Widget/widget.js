document.addEventListener("DOMContentLoaded", function () {
  const userId = "1"; // Replace with appropriate user identifier
  fetch(`/api/user-widgets/${userId}`)
    .then((response) => response.json())
    .then((userWidgets) => {
      userWidgets.forEach((userWidget) => {
        fetch(`/api/widgets/${userWidget.widgetName}`)
          .then((response) => response.text())
          .then((widgetContent) => {
            const widgetDiv = document.createElement("div");
            widgetDiv.innerHTML = widgetContent;
            document.getElementById("widget-container").appendChild(widgetDiv);
          });
      });
    });
});
