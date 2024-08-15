export class QuestionnaireManager {
  constructor(containerId, noAssignmentId, userID) {
    this.container = document.getElementById(containerId);
    this.noAssignmentElement = document.getElementById(noAssignmentId);
    this.userID = userID;
    this.apiBaseEndpoint = "/api/userQuestionnaires/user";

    if (!this.container) {
      console.error(`Container element with ID "${containerId}" not found.`);
      return;
    }

    if (!this.noAssignmentElement) {
      console.error(
        `No assignment element with ID "${noAssignmentId}" not found.`
      );
      return;
    }
  }

  async loadAssignedQuestionnaires() {
    const endpoint = `${this.apiBaseEndpoint}/${this.userID}/incomplete`;
    try {
      const response = await fetch(endpoint);
      if (!response.ok) {
        throw new Error("Network response was not ok");
      }
      const questionnaires = await response.json();
      console.log("Received questionnaires:", questionnaires); // Log the response
      this.displayQuestionnaires(questionnaires);
    } catch (error) {
      console.error(
        "There has been a problem with your fetch operation:",
        error
      );
    }
  }
  displayQuestionnaires(questionnaires) {
    this.container.innerHTML = ""; 

    if (questionnaires.length === 0) {
      this.noAssignmentElement.style.display = "block";
    } else {
      this.noAssignmentElement.style.display = "none";
      questionnaires.forEach((q) => {
        const questionnaireId = q.questionnaire.id; // Access the ID from the nested object
        console.log("Questionnaire ID:", questionnaireId); 

        if (questionnaireId) {
          // Ensure ID is not undefined
          const card = document.createElement("div");
          card.className = "questionnaire-item";
          card.innerHTML = `
                    <p>You have an incomplete questionnaire. Click <a href="/questionnaire/${questionnaireId}">here</a> to complete it.</p>
                `;
          this.container.appendChild(card);
        } else {
          console.error("Questionnaire ID is undefined or invalid", q);
        }
      });
    }
  }
}
