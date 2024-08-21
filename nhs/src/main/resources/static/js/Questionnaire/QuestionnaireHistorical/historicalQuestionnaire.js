import { UserQuestionnaireService } from "./historicalQuestionnaireService.js";

let table;

$(document).ready(function () {
  // Initialize DataTable and assign to the table variable
  table = $("#questionnaireTable").DataTable({
    paging: true,
    searching: false,
    lengthChange: false,
    pageLength: 5,
    order: [[0, "desc"]],
  });

  // Create an instance of the UserQuestionnaireService
  const questionnaireService = new UserQuestionnaireService();

  // Fetch completed user questionnaires and log them to the console
  questionnaireService
    .getCompletedUserQuestionnaires()
    .then((data) => {
      console.log("Fetched UserQuestionnaires:", data);
    })
    .catch((error) => {
      console.error("Error fetching data:", error);
    });

  // Load default category on page load
  loadCategoryData("function");
});

// Load category data (this would typically fetch data from the server)
function loadCategoryData(category) {
  // Example static data - replace with actual AJAX call
  const data = {
    function: [
      { date: "2024-08-21", question: "Question 1", result: "Result 1" },
      { date: "2024-08-21", question: "Question 2", result: "Result 2" },
      { date: "2024-07-15", question: "Question 1", result: "Result 3" },
      { date: "2024-07-15", question: "Question 2", result: "Result 4" },
    ],
    appearance: [
      { date: "2024-08-21", question: "Question 1", result: "Result A1" },
      { date: "2024-08-21", question: "Question 2", result: "Result A2" },
      { date: "2024-07-15", question: "Question 1", result: "Result A3" },
      { date: "2024-07-15", question: "Question 2", result: "Result A4" },
    ],
    // Add more categories and data here...
  };

  // Clear the table
  table.clear();

  // Populate the table with new data
  data[category].forEach((entry) => {
    table.row.add([entry.date, entry.question, entry.result]).draw(false);
  });
}

// Event listener for category tabs
$(".category-tab").on("click", function () {
  const category = $(this).data("category");
  loadCategoryData(category);
});
