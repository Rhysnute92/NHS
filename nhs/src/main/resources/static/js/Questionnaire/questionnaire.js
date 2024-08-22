import { QuestionnaireManager } from "./QuestionnaireManager.js";

document.addEventListener("DOMContentLoaded", async function () {
  // Extract the full query string from the URL
  const queryString = window.location.search;
  const urlParams = new URLSearchParams(queryString);

  // Extract both IDs from the URL
  const userQuestionnaireId = window.location.pathname.split("/").pop();
  const questionnaireId = urlParams.get("questionnaireId");

  console.log(`Extracted userQuestionnaireId from URL: ${userQuestionnaireId}`); // Debugging
  console.log(`Extracted questionnaireId from URL: ${questionnaireId}`); // Debugging

  if (
    !userQuestionnaireId ||
    userQuestionnaireId === "undefined" ||
    !questionnaireId
  ) {
    console.error(
      "No valid userQuestionnaireId or questionnaireId found in the URL"
    );
    return;
  }

  const questionnaireManager = new QuestionnaireManager();
  await questionnaireManager.loadQuestionnaire(
    userQuestionnaireId,
    questionnaireId
  );

  document
    .getElementById("questionnaire-form")
    .addEventListener("submit", async function (event) {
      event.preventDefault();
      const formData = new FormData(event.target);
      await questionnaireManager.submitQuestionnaire(
        event,
        userQuestionnaireId,
        questionnaireId
      );
    });

  document
    .getElementById("save-questionnaire")
    .addEventListener("click", async function () {
      const formData = new FormData(
        document.getElementById("questionnaire-form")
      );
      await questionnaireManager.saveQuestionnaire(
        formData,
        userQuestionnaireId
      );
    });
});
