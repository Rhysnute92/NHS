document.addEventListener("DOMContentLoaded", async function () {
  const questionnaireId = window.location.pathname.split("/").pop(); // Extract the ID from the URL

  try {
    // Fetch the questionnaire details
    const questionnaireResponse = await fetch(
      `/api/questionnaires/${questionnaireId}`
    );
    if (!questionnaireResponse.ok) {
      throw new Error("Failed to fetch questionnaire details");
    }
    const questionnaire = await questionnaireResponse.json();
    document.getElementById("questionnaire-title").textContent =
      questionnaire.title;
    document.getElementById("questionnaire-description").textContent =
      questionnaire.description;

    // Fetch the questions associated with this questionnaire
    const questionsResponse = await fetch(
      `/api/questions/questionnaire/${questionnaireId}`
    );
    if (!questionsResponse.ok) {
      throw new Error("Failed to fetch questionnaire questions");
    }
    const questions = await questionsResponse.json();

    const questionsContainer = document.getElementById("questions-container");

    // Populate the questions
    questions.forEach((question, index) => {
      const questionDiv = document.createElement("div");
      questionDiv.className = "question-item";

      const questionLabel = document.createElement("label");
      questionLabel.textContent = `${index + 1}. ${question.questionText}`;
      questionDiv.appendChild(questionLabel);

      if (question.questionResponseType === "text") {
        const input = document.createElement("input");
        input.type = "text";
        input.name = `question_${question.questionID}`;
        questionDiv.appendChild(input);
      } else if (question.questionResponseType === "number") {
        const input = document.createElement("input");
        input.type = "number";
        input.name = `question_${question.questionID}`;
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
