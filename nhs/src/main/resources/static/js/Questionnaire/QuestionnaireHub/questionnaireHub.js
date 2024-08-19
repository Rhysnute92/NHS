import { QuestionnaireHubManager } from "./questionnaireHubManager.js";

let manager;

document.addEventListener("DOMContentLoaded", function () {
  console.log("QuestionnaireHub script loaded and running");
  const patientId = 2; // TODO: replace with patientID fetch method when done.
  manager = new QuestionnaireHubManager(
    "questionnaire-container",
    "no-assignment"
  );
  manager.loadAssignedQuestionnaires(patientId);
});

// Handle popup functionality
const popup = document.getElementById("assignQuestionnairePopup");
const openPopupLink = document.querySelector(".action-link");
const cancelButton = document.getElementById("cancelButton");
const assignForm = document.getElementById("assignQuestionnaireForm");

// Open the popup when the link is clicked
openPopupLink.addEventListener("click", function (event) {
  event.preventDefault();
  popup.style.display = "block"; // Show the popup
  manager.loadAvailableQuestionnaires();
});

// Close the popup when the "Cancel" button is clicked
cancelButton.addEventListener("click", function () {
  popup.style.display = "none"; // Hide the popup
});

// Close the popup when clicking outside of the popup content
window.addEventListener("click", function (event) {
  if (event.target === popup) {
    popup.style.display = "none"; // Hide the popup
  }
});

// Handle form submission for assigning a new questionnaire
assignForm.addEventListener("submit", async function (event) {
  event.preventDefault();
  const questionnaireId = document.getElementById("questionnaireSelect").value;
  const dueDate = document.getElementById("dueDate").value;
  if (!questionnaireId || !dueDate) {
    alert("Please select a questionnaire and set a due date.");
    return;
  }

  try {
    await manager.assignQuestionnaire(patientId, questionnaireId, dueDate);
    popup.style.display = "none"; // Hide the popup after successful submission
  } catch (error) {
    console.error("Error assigning questionnaire:", error);
  }
});
