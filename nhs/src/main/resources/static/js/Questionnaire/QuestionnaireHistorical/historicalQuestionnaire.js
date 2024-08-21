import { UserQuestionnaireService } from "./historicalQuestionnaireService.js";

$(document).ready(function () {
  // Initialize DataTable
  $("#questionnaireTable").DataTable({
    paging: true,
    searching: false,
    lengthChange: false,
    pageLength: 5,
    order: [[0, "desc"]],
  });

  // Create an instance of the UserQuestionnaireService
  const questionnaireService = new UserQuestionnaireService();

  // Fetch completed user questionnaires and log them to the console
  questionnaireService
    .getCompletedUserQuestionnaires()
    .then((data) => {
      console.log("Fetched UserQuestionnaires:", data);
    })
    .catch((error) => {
      console.error("Error fetching data:", error);
    });
});
