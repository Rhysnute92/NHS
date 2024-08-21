import { fetchData, postData, putData } from "../../common/utils/apiUtility.js";

export class UserQuestionnaireService {
  constructor(baseUrl) {
    this.baseUrl = baseUrl || "/api/userQuestionnaires";
  }

  // Method to fetch completed UserQuestionnaires
  async getCompletedUserQuestionnaires() {
    try {
      const url = `${this.baseUrl}/user/completed`;
      const data = await fetchData(url);
      console.log("Completed UserQuestionnaires:", data);
      return data;
    } catch (error) {
      console.error("Error fetching completed UserQuestionnaires:", error);
      throw error;
    }
  }

  async getUserQuestions(userQuestionnaireId) {
    try {
      const url = `/api/userQuestions/userQuestionnaire/${userQuestionnaireId}`;
      const data = await fetchData(url);
      console.log(
        "UserQuestions for UserQuestionnaire:",
        userQuestionnaireId,
        data
      );
      return data;
    } catch (error) {
      console.error(
        `Error fetching UserQuestions for UserQuestionnaire ${userQuestionnaireId}:`,
        error
      );
      throw error;
    }
  }

  async getCompletedUserQuestionnairesForPatient(patientId) {
    try {
      const url = `${this.baseUrl}/provider/completed/${patientId}`;
      const data = await fetchData(url);
      console.log("Completed UserQuestionnaires for Patient:", data);
      return data;
    } catch (error) {
      console.error(
        "Error fetching completed UserQuestionnaires for Patient:",
        error
      );
      throw error;
    }
  }
}
