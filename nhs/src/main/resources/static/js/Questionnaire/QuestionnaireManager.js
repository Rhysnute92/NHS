import { fetchData, postData, putData } from "../common/utils/apiUtility.js";
import { QuestionnaireRenderer } from "./QuestionnaireRenderer.js";

export class QuestionnaireManager {
  constructor(containerId, noAssignmentId, userID) {
    this.containerId = containerId;
    this.noAssignmentId = noAssignmentId;
    this.userID = userID;
    this.renderer = new QuestionnaireRenderer(
      "questions-container",
      "questionnaire-title",
      "questionnaire-description"
    );

    // Debugging information
    console.log(`QuestionnaireManager initialized with userID: ${this.userID}`);
  }

  async loadAssignedQuestionnaires() {
    const endpoint = `/api/userQuestionnaires/user/${this.userID}/incomplete`;
    console.log(`Fetching assigned questionnaires from: ${endpoint}`);

    try {
      const questionnaires = await fetchData(endpoint);
      console.log(`Received questionnaires: `, questionnaires);

      if (!questionnaires || questionnaires.length === 0) {
        console.warn("No assigned questionnaires found.");
      }

      this.renderer.renderAssignedQuestionnaires(
        questionnaires,
        this.containerId,
        this.noAssignmentId
      );
    } catch (error) {
      console.error("Error loading assigned questionnaires:", error);
    }
  }

  async loadQuestionnaire(questionnaireId) {
    console.log(`Loading questionnaire with ID: ${questionnaireId}`);

    if (!questionnaireId || questionnaireId === "undefined") {
      console.error("Invalid questionnaireId provided:", questionnaireId);
      return;
    }

    try {
      const questionnaire = await fetchData(
        `/api/questionnaires/${questionnaireId}`
      );
      console.log("Fetched questionnaire details: ", questionnaire);

      this.renderer.renderQuestionnaireDetails(questionnaire);

      const questions = await fetchData(
        `/api/questions/questionnaire/${questionnaireId}`
      );
      console.log("Fetched questionnaire questions: ", questions);

      this.renderer.renderQuestions(questions);

      await this.updateUserQuestionnaireProgress(questionnaireId);
    } catch (error) {
      console.error("Error loading questionnaire:", error);
    }
  }

  async updateUserQuestionnaireProgress(questionnaireId) {
    console.log(`Updating questionnaire progress for ID: ${questionnaireId}`);

    try {
      const response = await putData(
        `/api/userQuestionnaires/${questionnaireId}`,
        {
          questionnaireInProgress: true,
          questionnaireStartDate: new Date().toISOString(),
        }
      );

      if (!response) {
        console.warn(
          "Received empty response after updating questionnaire progress."
        );
      } else {
        console.log("Questionnaire progress updated successfully.", response);
      }
    } catch (error) {
      console.error("Error updating user questionnaire progress:", error);
    }
  }

  async submitQuestionnaire(formData, questionnaireId) {
    console.log(`Submitting questionnaire with ID: ${questionnaireId}`);

    try {
      const responses = {};
      formData.forEach((value, key) => {
        responses[key] = value;
      });

      console.log("Collected form data: ", responses);

      await postData(`/api/userResponses/${questionnaireId}`, responses);

      alert("Thank you for completing the questionnaire!");
    } catch (error) {
      console.error("Error submitting questionnaire:", error);
      alert("An error occurred while submitting the questionnaire.");
    }
  }
}
