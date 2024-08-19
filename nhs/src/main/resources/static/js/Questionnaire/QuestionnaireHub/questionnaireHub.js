import { QuestionnaireHubManager } from "./questionnaireHubManager.js";

document.addEventListener("DOMContentLoaded", function () {
  console.log("QuestionnaireHub script loaded and running");
  const patientId = 2; // TODO: replace with patientID fetch method when done.
  const manager = new QuestionnaireHubManager(
    "questionnaire-container",
    "no-assignment"
  );
  manager.loadAssignedQuestionnaires(patientId);
});
