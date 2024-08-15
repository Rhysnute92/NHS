import { initializeCollapsible } from "./collapsible.js";
import { QuestionnaireManager } from "../Questionnaire/QuestionnaireManager.js";
import { fetchUserID } from "../common/utils/accountUtility.js";

document.addEventListener("DOMContentLoaded", async function () {
  initializeCollapsible();

  try {
    // Fetch the user ID dynamically
    const userID = await fetchUserID();

    // Check if userID is valid
    if (!userID) {
      console.error("Failed to fetch user ID");
      return;
    }

    // Instantiate the QuestionnaireManager
    const questionnaireManager = new QuestionnaireManager(
      "questionnaire-container",
      "no-assignment",
      userID
    );

    // Load assigned questionnaires
    await questionnaireManager.loadAssignedQuestionnaires();
  } catch (error) {
    console.error("Error initializing questionnaire manager:", error);
  }
});
