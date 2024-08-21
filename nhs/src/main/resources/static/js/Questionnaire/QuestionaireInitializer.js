import { QuestionnaireManager } from "./QuestionnaireManager.js";

export async function initializeQuestionnaireManager(
  containerId,
  noAssignmentId
) {
  const questionnaireManager = new QuestionnaireManager(
    containerId,
    noAssignmentId
  );

  await questionnaireManager.loadAssignedQuestionnaires();
  return questionnaireManager; // Return the instance for further use
}
