import { QuestionnaireManager } from "./QuestionnaireManager.js";
import { fetchUserID } from "../common/utils/accountUtility.js";

export async function initializeQuestionnaireManager(
  containerId,
  noAssignmentId
) {
  const userID = await fetchUserID();
  console.log("Initializing QuestionnaireManager with userID:", userID); // Debugging

  if (userID) {
    const questionnaireManager = new QuestionnaireManager(
      containerId,
      noAssignmentId,
      userID
    );
    await questionnaireManager.loadAssignedQuestionnaires();
  } else {
    console.error(
      "Failed to initialize QuestionnaireManager due to missing userID"
    );
  }
}
