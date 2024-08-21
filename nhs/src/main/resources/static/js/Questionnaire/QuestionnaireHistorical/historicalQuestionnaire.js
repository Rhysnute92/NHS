import { UserQuestionnaireService } from "./historicalQuestionnaireService.js";

let table;
const questionnaireService = new UserQuestionnaireService();

$(document).ready(function () {
  // Initialize DataTable and assign to the table variable
  table = $("#questionnaireTable").DataTable({
    paging: false, // Disable paging
    searching: false,
    lengthChange: false,
    info: false, // Disable the info text
    order: [[0, "desc"]],
  });

  // Fetch completed user questionnaires and display them
  questionnaireService
    .getCompletedUserQuestionnaires()
    .then((data) => {
      console.log("Fetched UserQuestionnaires:", data);
      populateDateColumn(data);
      if (data.length > 0) {
        generateCategoryTabs(data[0].userQuestionnaireId);
        loadCategoryData(data[0].userQuestionnaireId, "Function"); // Default to "Function" category
      }
    })
    .catch((error) => {
      console.error("Error fetching data:", error);
    });
});

function populateDateColumn(userQuestionnaires) {
  const dateColumn = $("#dateColumn");
  userQuestionnaires.forEach((questionnaire, index) => {
    const date = new Date(
      questionnaire.questionnaireCompletionDate
    ).toLocaleDateString("en-GB", {
      year: "numeric",
      month: "long",
      day: "numeric",
    });

    const button = $("<button>")
      .addClass("date-button")
      .text(date)
      .on("click", function () {
        loadCategoryData(questionnaire.userQuestionnaireId, "Function");
        generateCategoryTabs(questionnaire.userQuestionnaireId);
      });

    dateColumn.append(button);

    // Select the most recent by default
    if (index === 0) {
      button.addClass("active");
      loadCategoryData(questionnaire.userQuestionnaireId, "Function");
      generateCategoryTabs(questionnaire.userQuestionnaireId);
    }
  });
}

function generateCategoryTabs(userQuestionnaireId) {
  questionnaireService
    .getUserQuestions(userQuestionnaireId)
    .then((userQuestions) => {
      const categories = [
        ...new Set(userQuestions.map((q) => q.question.questionCategory)),
      ];

      const categoryTabs = $("#category-tabs");
      categoryTabs.empty(); // Clear existing tabs

      categories.forEach((category) => {
        const button = $("<button>")
          .addClass("category-tab")
          .text(category)
          .data("category", category)
          .on("click", function () {
            loadCategoryData(userQuestionnaireId, category);
          });

        categoryTabs.append(button);
      });
    })
    .catch((error) => {
      console.error("Error generating category tabs:", error);
    });
}

function loadCategoryData(userQuestionnaireId, category) {
  questionnaireService
    .getUserQuestions(userQuestionnaireId)
    .then((userQuestions) => {
      const filteredQuestions = userQuestions.filter(
        (q) => q.question.questionCategory === category
      );

      // Clear the table
      table.clear();

      // Populate the table with new data
      filteredQuestions.forEach((entry, index) => {
        const result =
          entry.userResponseText || entry.userResponseScore || "No Response";
        const dateCell =
          index === 0
            ? new Date(entry.responseDateTime).toLocaleDateString("en-GB")
            : "";
        table.row
          .add([dateCell, entry.question.questionText, result])
          .draw(false);
      });
    })
    .catch((error) => {
      console.error("Error loading category data:", error);
    });
}
