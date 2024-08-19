export class QuestionnaireHubRendererProviderView {
  renderAssignedQuestionnaires(questionnaires, containerId, noAssignmentId) {
    const container = document.getElementById(containerId);
    const noAssignmentElement = document.getElementById(noAssignmentId);
    container.innerHTML = ""; // Clear previous content

    if (questionnaires.length === 0) {
      noAssignmentElement.style.display = "block"; // Show no assignment message
      container.style.display = "none"; // Hide the questionnaire container
    } else {
      noAssignmentElement.style.display = "none"; // Hide no assignment message
      container.style.display = "block"; // Show the questionnaire container
      questionnaires.forEach((q) => {
        const card = document.createElement("div");
        card.className = "questionnaire-card";

        const title = document.createElement("h3");
        title.textContent = q.questionnaire.title; // Use the title from the questionnaire
        title.className = "questionnaire-card-title";
        card.appendChild(title);

        // Create a container for the details
        const detailsContainer = document.createElement("div");
        detailsContainer.className = "questionnaire-card-details";

        // Create and add the Created Date
        const createdDate = document.createElement("span");
        createdDate.innerHTML = `<strong>Created:</strong> ${new Date(
          q.questionnaireCreatedDate
        ).toLocaleDateString()}`;
        createdDate.className = "questionnaire-detail-item";
        detailsContainer.appendChild(createdDate);

        // Create and add the Due Date
        const dueDate = document.createElement("span");
        dueDate.innerHTML = `<strong>Due:</strong> ${new Date(
          q.questionnaireDueDate
        ).toLocaleDateString()}`;
        dueDate.className = "questionnaire-detail-item";
        detailsContainer.appendChild(dueDate);

        // Determine and add the Status
        const status = document.createElement("span");
        let statusText = "Not Started";
        let statusClass = "";
        if (q.questionnaireIsCompleted) {
          statusText = "Complete";
        } else if (q.questionnaireStartDate) {
          statusText = "In Progress";
          statusClass = "status-in-progress";
        }
        status.innerHTML = `<strong>Status:</strong> <span class="${statusClass}">${statusText}</span>`;
        status.className = "questionnaire-detail-item";
        detailsContainer.appendChild(status);

        // Append the details container to the card
        card.appendChild(detailsContainer);

        // Append the card to the container
        container.appendChild(card);
      });
    }
  }
}
