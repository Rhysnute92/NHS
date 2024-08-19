export class QuestionnaireHubRenderer {
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
        title.textContent = q.title;
        title.className = "questionnaire-card-title";

        const icon = document.createElement("span");
        icon.className = "questionnaire-document-icon";
        icon.innerHTML = "&#128196;"; // Unicode for document icon

        const actionLinkDiv = document.createElement("div");
        actionLinkDiv.className = "nhsuk-action-link";
        actionLinkDiv.innerHTML = `
                  <a class="nhsuk-action-link__link" href="/questionnaire/${q.id}">
                    <svg class="nhsuk-icon nhsuk-icon__arrow-right-circle" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" aria-hidden="true" width="36" height="36" fill="#007f3b">
                      <path d="M0 0h24v24H0z" fill="none"></path>
                      <path d="M12 2a10 10 0 0 0-9.95 9h11.64L9.74 7.05a1 1 0 0 1 1.41-1.41l5.66 5.65a1 1 0 0 1 0 1.42l-5.66 5.65a1 1 0 0 1-1.41 0 1 1 0 0 1 0-1.41L13.69 13H2.05A10 10 0 1 0 12 2z"></path>
                    </svg>
                    <span class="nhsuk-action-link__text">Start Questionnaire</span>
                  </a>
                `;

        card.appendChild(title);
        const contentDiv = document.createElement("div");
        contentDiv.className = "questionnaire-card-content";
        contentDiv.appendChild(icon);
        contentDiv.appendChild(actionLinkDiv);
        card.appendChild(contentDiv);

        container.appendChild(card);
      });
    }
  }
}
