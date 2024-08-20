import { QuestionnaireManager } from "./QuestionnaireManager.js";

document.addEventListener("DOMContentLoaded", async function () {
  const urlParams = new URLSearchParams(window.location.search);
  const questionnaireId = urlParams.get("questionnaireId");
  const userQuestionnaireId = window.location.pathname.split("/").pop();

  console.log(`Extracted userQuestionnaireId from URL: ${userQuestionnaireId}`);
  console.log(`Extracted questionnaireId from URL: ${questionnaireId}`);

  if (
    !userQuestionnaireId ||
    userQuestionnaireId === "undefined" ||
    !questionnaireId
  ) {
    console.error(
      "No valid userQuestionnaire ID or questionnaire ID found in the URL"
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
        formData,
        userQuestionnaireId
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
