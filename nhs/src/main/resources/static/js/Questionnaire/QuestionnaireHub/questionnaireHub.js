import { QuestionnaireHubManager } from "./QuestionnaireHubManager.js";

document.addEventListener("DOMContentLoaded", function () {
  const patientId = 2; // TODO: replace with patientID fetch method when done.
  const manager = new QuestionnaireHubManager(
    "questionnaire-container",
    "no-assignment"
  );
  manager.loadAssignedQuestionnaires(patientId);
});
