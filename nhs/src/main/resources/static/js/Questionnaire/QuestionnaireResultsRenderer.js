/* export class QuestionnaireResultsRenderer {
  constructor() {
    this.tableBody = document.querySelector("#resultsTable tbody");
    this.currentSection = "function";
  }

  renderQuestionnaireList(data, manager) {
    const listContainer = document.getElementById("questionnaire-list");
    listContainer.innerHTML = ""; // Clear previous data

    data.forEach((questionnaire, index) => {
      const li = document.createElement("li");
      li.textContent = new Date(
        questionnaire.questionnaireCompletionDate
      ).toLocaleDateString();
      li.dataset.id = questionnaire.userQuestionnaireId;
      li.addEventListener("click", async () => {
        const userResponses = await manager.loadUserResponses(li.dataset.id);
        const formattedResponses =
          manager.dataFormatter.formatUserResponses(userResponses);
        this.renderUserResponses(formattedResponses);
        manager.selectedQuestionnaire = questionnaire.userQuestionnaireId;
      });
      listContainer.appendChild(li);
    });

    this.updatePaginationControls(manager);
  }

  updatePaginationControls(manager) {
    const paginationControls = document.getElementById("pagination-controls");
    paginationControls.innerHTML = `
      <button id="prevPage" ${
        manager.currentPage === 1 ? "disabled" : ""
      }>Previous</button>
      <button id="nextPage" ${
        manager.currentPage >=
        Math.ceil(manager.completedQuestionnaires.length / manager.itemsPerPage)
          ? "disabled"
          : ""
      }>Next</button>
    `;

    document.getElementById("prevPage").addEventListener("click", () => {
      if (manager.currentPage > 1) {
        manager.setCurrentPage(manager.currentPage - 1);
        this.renderQuestionnaireList(manager.getCurrentPageData(), manager);
      }
    });

    document.getElementById("nextPage").addEventListener("click", () => {
      if (
        manager.currentPage <
        Math.ceil(manager.completedQuestionnaires.length / manager.itemsPerPage)
      ) {
        manager.setCurrentPage(manager.currentPage + 1);
        this.renderQuestionnaireList(manager.getCurrentPageData(), manager);
      }
    });
  }

  renderUserResponses(formattedResponses) {
    this.clearTable();
    const sectionFilteredResponses = formattedResponses.filter(
      (response) => response.category === this.currentSection
    );

    sectionFilteredResponses.forEach((response) => {
      const tr = document.createElement("tr");
      tr.innerHTML = `
        <td>${response.questionText}</td>
        <td>${response.responseText}</td>
      `;
      this.tableBody.appendChild(tr);
    });
  }

  clearTable() {
    this.tableBody.innerHTML = "";
  }

  setSection(section) {
    this.currentSection = section;
  }
}
 */
