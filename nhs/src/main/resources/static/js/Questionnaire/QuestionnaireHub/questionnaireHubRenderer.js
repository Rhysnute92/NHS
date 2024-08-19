export class QuestionnaireHubRendererProviderView {
  renderAssignedQuestionnaires(questionnaires, containerId, noAssignmentId) {
    const container = document.getElementById(containerId);
    const noAssignmentElement = document.getElementById(noAssignmentId);
    container.innerHTML = ""; // Clear previous content

    if (questionnaires.length === 0) {
      noAssignmentElement.style.display = "block"; // Show no assignment message
    } else {
      noAssignmentElement.style.display = "none"; // Hide no assignment message
      questionnaires.forEach((q) => {
        const card = document.createElement("div");
        card.className = "questionnaire-card";

        const title = document.createElement("h3");
        title.textContent = q.questionnaire.title; // Use the title from the questionnaire
        title.className = "questionnaire-card-title";

        const icon = document.createElement("span");
        icon.className = "questionnaire-document-icon";
        icon.innerHTML = "&#128196;"; // Unicode for document icon

        card.appendChild(title);
        const contentDiv = document.createElement("div");
        contentDiv.className = "questionnaire-card-content";
        contentDiv.appendChild(icon);
        card.appendChild(contentDiv);

        container.appendChild(card);
      });
    }
  }
}
