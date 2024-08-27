import { QuestionnaireHubManager } from "./QuestionnaireHubManager.js";

let manager;
let patientId;

document.addEventListener("DOMContentLoaded", function () {
  console.log("QuestionnaireHub script loaded and running");
  patientId = 2; // TODO: replace with patientID fetch method when done.
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

assignForm.addEventListener("submit", async function (event) {
  event.preventDefault();

  const day = document.getElementById("dueDay").value;
  const month = document.getElementById("dueMonth").value;
  const year = document.getElementById("dueYear").value;

  // Validate the day, month, and year
  if (!isValidDate(day, month, year)) {
    alert(
      "Please enter a valid date in the format DD/MM/YYYY and ensure it is in the future."
    );
    return;
  }

  const questionnaireId = document.getElementById("questionnaireSelect").value;
  const dueDate = `${year}-${formatMonth(month)}-${formatDay(day)}`; // Formatted due date for the backend

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

// Function to check if a given date is valid and in the future
function isValidDate(day, month, year) {
  const months = {
    January: 31,
    February: 29,
    March: 31,
    April: 30,
    May: 31,
    June: 30,
    July: 31,
    August: 31,
    September: 30,
    October: 31,
    November: 30,
    December: 31,
  };

  // Check if month is valid
  if (!months[month]) {
    return false;
  }

  const maxDay = months[month];

  // Check if day is valid
  if (isNaN(day) || day < 1 || day > maxDay) {
    return false;
  }

  // Create a date object for the due date and today
  const dueDate = new Date(`${month} ${day}, ${year}`);
  const today = new Date();

  // Check if the date is in the future
  if (dueDate <= today) {
    return false;
  }

  return true;
}

// Helper functions to format month and day
function formatMonth(month) {
  const months = {
    January: "01",
    February: "02",
    March: "03",
    April: "04",
    May: "05",
    June: "06",
    July: "07",
    August: "08",
    September: "09",
    October: "10",
    November: "11",
    December: "12",
  };
  return months[month] || "01";
}

function formatDay(day) {
  return day.padStart(2, "0"); // Ensure day is two digits
}
