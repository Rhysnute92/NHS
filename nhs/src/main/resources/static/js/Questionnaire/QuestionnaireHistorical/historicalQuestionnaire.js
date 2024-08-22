import { UserQuestionnaireService } from "./historicalQuestionnaireService.js";

let table;
const questionnaireService = new UserQuestionnaireService();
let chart;

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
        generateTrendButtons(data); // Generate trend buttons for each category
        loadTrendChart(data, "Function"); // Load the trend chart for the default category
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

function setActiveTrendButton(category) {
  $(".trend-button").removeClass("active"); // Remove active class from all buttons
  $(`.trend-button[data-category="${category}"]`).addClass("active"); // Add active class to the clicked button
}

function generateTrendButtons(userQuestionnaires) {
  questionnaireService
    .getUserQuestions(userQuestionnaires[0].userQuestionnaireId)
    .then((userQuestions) => {
      const categories = [
        ...new Set(userQuestions.map((q) => q.question.questionCategory)),
      ];

      const trendButtons = $("#trend-buttons");
      trendButtons.empty(); // Clear existing buttons

      categories.forEach((category) => {
        const button = $("<button>")
          .addClass("trend-button btn btn-primary")
          .text(category)
          .data("category", category)
          .on("click", function () {
            loadTrendChart(userQuestionnaires, category);
            setActiveTrendButton(category); // Set the clicked button as active
          });

        trendButtons.append(button);
      });

      // Set the first button as active by default
      setActiveTrendButton(categories[0]);
    })
    .catch((error) => {
      console.error("Error generating trend buttons:", error);
    });
}

function loadTrendChart(userQuestionnaires, category) {
  const labels = [];
  const dataPoints = [];

  userQuestionnaires.forEach((questionnaire) => {
    questionnaireService
      .getUserQuestions(questionnaire.userQuestionnaireId)
      .then((userQuestions) => {
        const filteredQuestions = userQuestions.filter(
          (q) => q.question.questionCategory === category
        );

        let totalScore = 0;
        let answeredQuestionsCount = 0;

        filteredQuestions.forEach((entry) => {
          if (typeof entry.userResponseScore === "number") {
            totalScore += entry.userResponseScore;
            answeredQuestionsCount++;
          }
        });

        const averageScore =
          answeredQuestionsCount > 0
            ? (totalScore / answeredQuestionsCount).toFixed(2)
            : 0;

        labels.push(
          new Date(
            questionnaire.questionnaireCompletionDate
          ).toLocaleDateString("en-GB")
        );
        dataPoints.push(averageScore);

        if (chart) {
          chart.destroy();
        }

        const ctx = $("#trendChart");
        chart = new Chart(ctx, {
          type: "line",
          data: {
            labels: labels,
            datasets: [
              {
                label: `Average Score for ${category}`,
                data: dataPoints,
                borderColor: "rgba(75, 192, 192, 1)",
                fill: false,
                tension: 0.1,
              },
            ],
          },
          options: {
            scales: {
              x: {
                title: {
                  display: true,
                  text: "Completion Date",
                },
              },
              y: {
                beginAtZero: true,
                title: {
                  display: true,
                  text: "Average Score",
                },
              },
            },
          },
        });
      })
      .catch((error) => {
        console.error("Error loading trend chart data:", error);
      });
  });
}
