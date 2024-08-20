import { fetchData, postData, putData } from "../common/utils/apiUtility.js";
import { QuestionnaireRenderer } from "./QuestionnaireRenderer.js";

export class QuestionnaireManager {
  constructor(containerId, noAssignmentId) {
    this.containerId = containerId;
    this.noAssignmentId = noAssignmentId;
    this.renderer = new QuestionnaireRenderer(
      "questions-container",
      "questionnaire-title",
      "questionnaire-description"
    );

    // Bind the form submission event
    const form = document.getElementById("questionnaire-form");
    if (form) {
      form.addEventListener("submit", (event) =>
        this.submitQuestionnaire(event)
      );
    }
  }

  async loadAssignedQuestionnaires() {
    const endpoint = `/api/userQuestionnaires/user/incomplete`;
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

  async loadCompletedQuestionnaires() {
    // Check if we already have the data
    if (this.completedQuestionnaires) {
      return this.completedQuestionnaires;
    }

    const endpoint = `/api/userQuestionnaires/user/completed`;
    console.log(
      `Fetching completed questionnaires for user ${this.userID} from: ${endpoint}`
    );

    try {
      const completedQuestionnaires = await fetchData(endpoint);
      console.log(
        "Received completed questionnaires:",
        completedQuestionnaires
      );

      // Simply store the raw data
      this.completedQuestionnaires = completedQuestionnaires;

      // Return the data for use in rendering
      return this.completedQuestionnaires;
    } catch (error) {
      console.error("Error loading completed questionnaires:", error);
      return [];
    }
  }

  async loadQuestionnaire(userQuestionnaireId, questionnaireId) {
    console.log(
      `Loading questionnaire with userQuestionnaireId: ${userQuestionnaireId} and questionnaireId: ${questionnaireId}`
    );

    if (
      !userQuestionnaireId ||
      userQuestionnaireId === "undefined" ||
      !questionnaireId
    ) {
      console.error("Invalid userQuestionnaireId or questionnaireId provided");
      return;
    }

    try {
      const questionnaire = await fetchData(
        `/api/questionnaires/${questionnaireId}`
      );
      console.log("Fetched questionnaire details: ", questionnaire);

      const form = document.getElementById("questionnaire-form");
      form.setAttribute("data-user-questionnaire-id", userQuestionnaireId);

      this.renderer.renderQuestionnaireDetails(questionnaire);

      const questions = await fetchData(
        `/api/questions/questionnaire/${questionnaireId}`
      );
      console.log("Fetched questionnaire questions: ", questions);

      this.renderer.renderQuestions(questions);

      // Fetch and populate user's previous responses using userQuestionnaireId
      const previousResponses = await this.loadUserResponses(
        userQuestionnaireId
      );
      if (previousResponses) {
        this.populatePreviousResponses(previousResponses);
      }

      /* await this.updateUserQuestionnaireProgress(userQuestionnaireId); */
    } catch (error) {
      console.error("Error loading questionnaire:", error);
    }
  }

  async loadUserResponses(userQuestionnaireId) {
    console.log(
      `Loading user responses for userQuestionnaireId: ${userQuestionnaireId}`
    );

    try {
      const responses = await fetchData(
        `/api/userQuestions/userQuestionnaire/${userQuestionnaireId}`
      );
      console.log("Fetched user responses: ", responses);
      return responses;
    } catch (error) {
      console.error("Error loading user responses:", error);
      return [];
    }
  }

  populatePreviousResponses(responses) {
    if (!responses) return;

    responses.forEach((response) => {
      const questionId = response.question.questionID;
      const responseValue =
        response.userResponseScore || response.userResponseText;

      // If the response is a score (radio button)
      if (response.userResponseScore !== null) {
        const radioInput = document.querySelector(
          `input[name="question_${questionId}"][value="${responseValue}"]`
        );
        if (radioInput) {
          radioInput.checked = true;
        }
      } else if (response.userResponseText) {
        // If the response is text input
        const textInput = document.querySelector(
          `input[name="question_${questionId}"]`
        );
        if (textInput) {
          textInput.value = responseValue;
        }
      }
    });

    console.log("Populated form with previous responses.");
  }

  /*   async updateUserQuestionnaireProgress(questionnaireId) {
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
  } */

  async saveQuestionnaire(formData, userQuestionnaireId) {
    console.log(
      `Saving form data for userQuestionnaireId: ${userQuestionnaireId}`
    );

    const responses = {};
    formData.forEach((value, key) => {
      responses[key.replace("question_", "")] = value;
    });
    console.log(responses);

    try {
      await postData(
        `/api/userQuestionnaires/save/${userQuestionnaireId}`,
        responses
      );
      alert(
        "Your responses have been saved. You can return later to complete the questionnaire."
      );
      window.location.href = "/management";
    } catch (error) {
      console.error("Error saving form data:", error);
      alert("An error occurred while saving your responses.");
    }
  }

  async submitQuestionnaire(event, userQuestionnaireId, questionnaireId) {
    event.preventDefault(); // Prevent the default form submission behavior

    console.log("event.target:", event.target);
    console.log(
      "Is event.target an HTMLFormElement?",
      event.target instanceof HTMLFormElement
    );
    console.log("event.target.dataset:", event.target.dataset);

    const formData = new FormData(event.target);

    if (!userQuestionnaireId || !questionnaireId) {
      console.error("UserQuestionnaire ID or Questionnaire ID is missing.");
      return;
    }

    // Validation: Check if all required questions are answered
    const isValid = this.validateForm(event.target);
    if (!isValid) {
      alert("Please answer all questions before submitting the questionnaire.");
      return;
    }

    console.log(
      `Submitting form for userQuestionnaireId: ${userQuestionnaireId}, questionnaireId: ${questionnaireId}`
    );

    const responses = {};
    formData.forEach((value, key) => {
      responses[key.replace("question_", "")] = value;
    });

    try {
      const response = await postData(
        `/api/userQuestions/submit/${userQuestionnaireId}`, // Correct endpoint
        responses
      );
      console.log("Form submitted successfully:", response);

      // Redirect to the questionnaire details page
      window.location.href = `/questionnaire/${userQuestionnaireId}/details`;
    } catch (error) {
      console.error("Error submitting form:", error);
      alert("An error occurred while submitting the questionnaire.");
    }
  }

  validateForm(form) {
    let isValid = true;

    // Validate all text inputs
    const textInputs = form.querySelectorAll('input[type="text"]');
    textInputs.forEach((input) => {
      if (input.value.trim() === "") {
        isValid = false;
        input.closest(".question-item").classList.add("error");
      } else {
        input.closest(".question-item").classList.remove("error");
      }
    });

    // Validate radio button groups
    const radioGroups = {};
    const radioButtons = form.querySelectorAll('input[type="radio"]');
    radioButtons.forEach((radio) => {
      if (!radioGroups[radio.name]) {
        radioGroups[radio.name] = false;
      }
      if (radio.checked) {
        radioGroups[radio.name] = true;
      }
    });

    for (let groupName in radioGroups) {
      if (!radioGroups[groupName]) {
        isValid = false;
        const radios = form.querySelectorAll(`input[name="${groupName}"]`);
        radios.forEach((radio) => {
          radio.closest(".question-item").classList.add("error");
        });
      } else {
        const radios = form.querySelectorAll(`input[name="${groupName}"]`);
        radios.forEach((radio) => {
          radio.closest(".question-item").classList.remove("error");
        });
      }
    }

    return isValid;
  }
}
