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
      this.displayQuestionnaires(questionnaires);
    } catch (error) {
      console.error(
        "There has been a problem with your fetch operation:",
        error
      );
    }
  }
  displayQuestionnaires(questionnaires) {
    this.container.innerHTML = ""; // Clear the container

    if (questionnaires.length === 0) {
      this.noAssignmentElement.style.display = "block"; // Show no assignment message
    } else {
      this.noAssignmentElement.style.display = "none"; // Hide no assignment message
      questionnaires.forEach((q) => {
        const card = document.createElement("div");
        card.className = "questionnaire-item";
        card.innerHTML = `
            <p>You have an incomplete questionnaire. Click <a th:href="@{/questionnaire/${q.id}}">here</a> to complete it.</p>
          `;
        this.container.appendChild(card);
      });
    }
  }
}
