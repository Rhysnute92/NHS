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
      populateDateSelect(data);
      if (data.length > 0) {
        generateCategoryTabs(data[0]);
        loadCategoryData(data[0], "Function"); // Default to "Function" category
      }
    })
    .catch((error) => {
      console.error("Error fetching data:", error);
    });
});

function populateDateSelect(userQuestionnaires) {
  const dateSelect = $("#date-select");
  dateSelect.empty(); // Clear existing options

  userQuestionnaires.forEach((questionnaire, index) => {
    const date = new Date(
      questionnaire.questionnaireCompletionDate
    ).toLocaleDateString("en-GB", {
      year: "numeric",
      month: "long",
      day: "numeric",
    });

    const option = $("<option>")
      .val(index)
      .text(date)
      .data("questionnaire", questionnaire);

    dateSelect.append(option);
  });

  // Automatically load the first questionnaire's data
  dateSelect.on("change", function () {
    const selectedIndex = $(this).val();
    const selectedQuestionnaire = $(this)
      .find("option:selected")
      .data("questionnaire");
    loadCategoryData(selectedQuestionnaire, "Function"); // Default to "Function" category
    generateCategoryTabs(selectedQuestionnaire);
  });

  // Trigger the change event to load the first questionnaire's data by default
  dateSelect.trigger("change");
}

function generateCategoryTabs(userQuestionnaire) {
  questionnaireService
    .getUserQuestions(userQuestionnaire.userQuestionnaireId)
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
            loadCategoryData(userQuestionnaire, category);
          });

        categoryTabs.append(button);
      });
    })
    .catch((error) => {
      console.error("Error generating category tabs:", error);
    });
}

function loadCategoryData(userQuestionnaire, category) {
  questionnaireService
    .getUserQuestions(userQuestionnaire.userQuestionnaireId)
    .then((userQuestions) => {
      const filteredQuestions = userQuestions.filter(
        (q) => q.question.questionCategory === category
      );

      let totalScore = 0;
      let answeredQuestionsCount = 0;

      // Clear the table
      table.clear();

      // Populate the table with new data
      filteredQuestions.forEach((entry, index) => {
        const result = entry.userResponseText || entry.userResponseScore || 0;

        // Accumulate scores for calculation if it's a number
        if (typeof entry.userResponseScore === "number") {
          totalScore += entry.userResponseScore;
          answeredQuestionsCount++;
        }

        const dateCell =
          index === 0
            ? new Date(
                userQuestionnaire.questionnaireCompletionDate
              ).toLocaleDateString("en-GB")
            : "";

        table.row
          .add([dateCell, entry.question.questionText, result])
          .draw(false);
      });

      // Calculate the average score
      const averageScore =
        answeredQuestionsCount > 0
          ? (totalScore / answeredQuestionsCount).toFixed(2)
          : 0;

      // Add the final row for the average score
      table.row
        .add([
          "",
          `<strong>Average Score for ${category}</strong>`,
          `<strong>${averageScore}</strong>`,
        ])
        .draw(false);
    })
    .catch((error) => {
      console.error("Error loading category data:", error);
    });
}
