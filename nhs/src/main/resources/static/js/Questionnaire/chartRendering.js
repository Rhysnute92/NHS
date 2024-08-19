/* document.addEventListener("DOMContentLoaded", function () {
  // Iterate through each category
  for (var categoryKey in responsesForCategory) {
    if (responsesForCategory.hasOwnProperty(categoryKey)) {
      // Check if the data for this category is not null and has items
      if (
        responsesForCategory[categoryKey] &&
        responsesForCategory[categoryKey].length > 0
      ) {
        var ctx = document
          .getElementById("chart-" + categoryKey)
          .getContext("2d");
        var data = responsesForCategory[categoryKey];

        // Process data for the chart (e.g., extract labels and values)
        var labels = data.map(function (item) {
          return item.question.questionText;
        });
        var values = data.map(function (item) {
          return item.userResponseScore || item.userResponseText; // Adjust according to your data
        });

        // Create the chart
        new Chart(ctx, {
          type: "bar",
          data: {
            labels: labels,
            datasets: [
              {
                label: "Responses",
                data: values,
                backgroundColor: "rgba(75, 192, 192, 0.2)",
                borderColor: "rgba(75, 192, 192, 1)",
                borderWidth: 1,
              },
            ],
          },
          options: {
            scales: {
              y: {
                beginAtZero: true,
              },
            },
          },
        });
      }
    }
  }
});
 */