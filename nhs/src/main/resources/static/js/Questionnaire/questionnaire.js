document.addEventListener("DOMContentLoaded", async function () {
  const questionnaireId = window.location.pathname.split("/").pop(); // Extract the ID from the URL
  try {
    const response = await fetch(`/api/questionnaires/${questionnaireId}`);
    if (!response.ok) {
      throw new Error("Failed to fetch questionnaire details");
    }

    const questionnaire = await response.json();
    document.getElementById("questionnaire-title").textContent =
      questionnaire.title;
    document.getElementById("questionnaire-description").textContent =
      questionnaire.description;

    const questionsContainer = document.getElementById("questions-container");

    questionnaire.questions.forEach((question, index) => {
      const questionDiv = document.createElement("div");
      questionDiv.className = "question-item";

      const questionLabel = document.createElement("label");
      questionLabel.textContent = `${index + 1}. ${question.text}`;
      questionDiv.appendChild(questionLabel);

      if (question.responseType === "text") {
        const input = document.createElement("input");
        input.type = "text";
        input.name = `question_${question.id}`;
        questionDiv.appendChild(input);
      } else if (question.responseType === "number") {
        const input = document.createElement("input");
        input.type = "number";
        input.name = `question_${question.id}`;
        questionDiv.appendChild(input);
      }
      // Add other response types as necessary

      questionsContainer.appendChild(questionDiv);
    });

    // Update the UserQuestionnaire to indicate it is in progress
    await fetch(`/api/userQuestionnaires/${questionnaireId}`, {
      method: "PUT",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        questionnaireInProgress: true,
        questionnaireStartDate: new Date().toISOString(),
      }),
    });
  } catch (error) {
    console.error("Error loading questionnaire:", error);
  }
});

document
  .getElementById("questionnaire-form")
  .addEventListener("submit", async function (event) {
    event.preventDefault();
    // Collect responses and submit
  });
