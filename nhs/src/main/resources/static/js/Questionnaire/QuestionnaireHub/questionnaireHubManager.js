import { fetchData, postData } from "../../common/utils/apiUtility.js";
import { QuestionnaireHubRendererProviderView } from "./QuestionnaireHubRendererProviderView.js";

export class QuestionnaireHubManager {
  constructor(containerId, noAssignmentId) {
    this.containerId = containerId;
    this.noAssignmentId = noAssignmentId;
    this.renderer = new QuestionnaireHubRendererProviderView();
  }

  async loadAssignedQuestionnaires(patientId) {
    const endpoint = `/api/userQuestionnaires/provider/incomplete/${patientId}`; // Updated endpoint
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

  async loadAvailableQuestionnaires() {
    const endpoint = `/api/questionnaires`; // Endpoint to get all questionnaires
    console.log(`Fetching available questionnaires from: ${endpoint}`);

    try {
      const questionnaires = await fetchData(endpoint);
      console.log(`Received available questionnaires: `, questionnaires);

      // Use the renderer to populate the select element
      this.renderer.renderQuestionnaireOptions(
        questionnaires,
        "questionnaireSelect"
      );
    } catch (error) {
      console.error("Error loading available questionnaires:", error);
    }
  }

  async assignQuestionnaire(patientId, questionnaireId, dueDate) {
    const endpoint = `/api/userQuestionnaires/provider/${patientId}/assign-questionnaire`;

    // Prepare the request payload
    const data = {
      questionnaire: {
        id: questionnaireId,
      },
      questionnaireDueDate: dueDate, // Ensure this is in the correct format (e.g., "2025-06-15")
      questionnaireInProgress: false,
      questionnaireIsCompleted: false,
    };

    try {
      // Send the POST request
      await postData(endpoint, data);
      alert("Questionnaire assigned successfully.");

      // Reload the assigned questionnaires
      await this.loadAssignedQuestionnaires(patientId);
    } catch (error) {
      console.error("Error assigning questionnaire:", error);
      alert("Failed to assign the questionnaire.");
    }
  }
}
