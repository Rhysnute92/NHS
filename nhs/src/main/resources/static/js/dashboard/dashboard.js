import { PatientInfo } from './patientInfo.js';

document.addEventListener("DOMContentLoaded", function() {
  updateDashboard();
});

function updateDashboard() {
  // const patientName = PatientInfo.fetchPatientName();
  const lastLoginTime = PatientInfo.fetchLastLoginTime();

  // const welcomeMessageElement = document.getElementById("welcome-message");
  const lastLoggedInElement = document.getElementById("last-logged-in");

  // welcomeMessageElement.textContent += patientName;
  lastLoggedInElement.textContent = lastLoginTime;
}
