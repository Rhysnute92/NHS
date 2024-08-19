import { QuestionnaireManager } from "./QuestionnaireManager.js";

document.addEventListener("DOMContentLoaded", async function () {
  const questionnaireId = window.location.pathname.split("/").pop();

  console.log(`Extracted questionnaireId from URL: ${questionnaireId}`); // Debugging

  if (!questionnaireId || questionnaireId === "undefined") {
    console.error("No valid questionnaire ID found in the URL");
    return;
  }

  const questionnaireManager = new QuestionnaireManager();
  await questionnaireManager.loadQuestionnaire(questionnaireId);

  document
    .getElementById("questionnaire-form")
    .addEventListener("submit", async function (event) {
      event.preventDefault();
      const formData = new FormData(event.target);
      await questionnaireManager.submitQuestionnaire(formData, questionnaireId);
    });
    
  document
    .getElementById("save-questionnaire")
    .addEventListener("click", async function () {
      const formData = new FormData(
        document.getElementById("questionnaire-form")
      );
      await questionnaireManager.saveQuestionnaire(formData, questionnaireId);
    });
});
