import { fetchData, postData, putData } from "../common/utils/apiUtility.js";
import { QuestionnaireRenderer } from "./QuestionnaireRenderer.js";
import { ChartRenderer } from "./ChartRenderer.js";

export class QuestionnaireManager {
  constructor(containerId, noAssignmentId, userID) {
    this.containerId = containerId;
    this.noAssignmentId = noAssignmentId;
    this.userID = userID;
    this.chartRenderer = new ChartRenderer("resultsChart");
    this.renderer = new QuestionnaireRenderer(
      "questions-container",
      "questionnaire-title",
      "questionnaire-description"
    );

    // Debugging information
    console.log(`QuestionnaireManager initialized with userID: ${this.userID}`);

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

  async loadAndRenderTrendData() {
    try {
      const userQuestionnaires = await fetchData(
        `/api/userQuestionnaires/user/completed`
      );
      console.log("Fetched completed user questionnaires:", userQuestionnaires);

      const trendDataPromises = userQuestionnaires.map(async (uq) => {
        const responses = await fetchData(
          `/api/userQuestions/userQuestionnaire/${uq.userQuestionnaireId}`
        );
        console.log(
          `Fetched responses for questionnaire ${uq.userQuestionnaireId}:`,
          responses
        );

        return {
          date: uq.questionnaireCompletionDate,
          ...this.processResponsesForTrend(responses),
        };
      });

      const trendData = await Promise.all(trendDataPromises);
      console.log("Final trend data to be rendered:", trendData);

      this.chartRenderer.processAndRenderData(trendData);
    } catch (error) {
      console.error("Error loading trend data:", error);
    }
  }

  processResponsesForTrend(responses) {
    const trendCategories = {
      Function: 0,
      Appearance: 0,
      Symptoms: 0,
      Emotion: 0,
      QualityOfLife: 0,
    };

    responses.forEach((response) => {
      const category = response.question.questionCategory;
      if (trendCategories.hasOwnProperty(category)) {
        trendCategories[category] += response.userResponseScore || 0;
      }
    });

    return trendCategories;
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

      const form = document.getElementById("questionnaire-form");
      form.setAttribute("data-questionnaire-id", questionnaireId);

      this.renderer.renderQuestionnaireDetails(questionnaire);

      const questions = await fetchData(
        `/api/questions/questionnaire/${questionnaireId}`
      );
      console.log("Fetched questionnaire questions: ", questions);

      this.renderer.renderQuestions(questions);

      // Fetch and populate user's previous responses
      const previousResponses = await this.loadUserResponses(questionnaireId);
      if (previousResponses) {
        this.populatePreviousResponses(previousResponses);
      }

      await this.updateUserQuestionnaireProgress(questionnaireId);
    } catch (error) {
      console.error("Error loading questionnaire:", error);
    }
  }

  async loadUserResponses(questionnaireId) {
    console.log(
      `Loading user responses for questionnaire ID: ${questionnaireId}`
    );

    try {
      const responses = await fetchData(
        `/api/userQuestions/userQuestionnaire/${questionnaireId}`
      );
      console.log("Fetched user responses: ", responses);
      return responses;
    } catch (error) {
      console.error("Error loading user responses:", error);
      return null;
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

  async saveQuestionnaire(formData, questionnaireId) {
    console.log(`Saving form data for questionnaire ID: ${questionnaireId}`);

    const responses = {};
    formData.forEach((value, key) => {
      responses[key.replace("question_", "")] = value;
    });

    try {
      await postData(
        `/api/userQuestionnaires/save/${questionnaireId}`,
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

  async submitQuestionnaire(event) {
    event.preventDefault(); // Prevent the default form submission behavior

    console.log("event.target:", event.target);
    console.log(
      "Is event.target an HTMLFormElement?",
      event.target instanceof HTMLFormElement
    );
    console.log("event.target.dataset:", event.target.dataset);

    const formData = new FormData(event.target);
    const questionnaireId = event.target.dataset.questionnaireId;

    if (!questionnaireId) {
      console.error("Questionnaire ID is missing in the form dataset.");
      return;
    }

    // Validation: Check if all required questions are answered
    const isValid = this.validateForm(event.target);
    if (!isValid) {
      alert("Please answer all questions before submitting the questionnaire.");
      return;
    }

    console.log(`Submitting form for questionnaire ID: ${questionnaireId}`);

    const responses = {};
    formData.forEach((value, key) => {
      responses[key.replace("question_", "")] = value;
    });

    try {
      const response = await postData(
        `/api/userQuestions/submit/${questionnaireId}`, // Correct endpoint
        responses
      );
      console.log("Form submitted successfully:", response);

      // Redirect to the questionnaire details page
      window.location.href = `/questionnaire/${questionnaireId}/details`;
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
