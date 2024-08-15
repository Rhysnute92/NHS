import { initializeCollapsible } from "./collapsible.js";
import { initializeQuestionnaireManager } from "../questionnaire/QuestionaireInitializer.js";
import { initializeTaskManager } from "../tasks/TaskInitializer.js";
import { WorkerManager } from "../tasks/WorkerManager.js";
import { fetchUserID } from "../common/utils/accountUtility.js";

document.addEventListener("DOMContentLoaded", async function () {
  initializeCollapsible();

  const workerManager = new WorkerManager(
    "/js/tasks/worker.js",
    "/usertask/task-update/batch"
  );

  const questionnaireContainerId = "questionnaire-container";
  const noAssignmentContainerId = "no-assignment";

  await initializeQuestionnaireManager(
    questionnaireContainerId,
    noAssignmentContainerId
  );

  const userID = await fetchUserID();
  if (userID) {
    initializeTaskManager(userID, workerManager.eventQueue);
  }
});
