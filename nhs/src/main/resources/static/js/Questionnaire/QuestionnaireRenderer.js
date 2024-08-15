export class QuestionnaireRenderer {
  constructor(questionsContainerId, titleId, descriptionId) {
    this.questionsContainer = document.getElementById(questionsContainerId);
    this.titleElement = document.getElementById(titleId);
    this.descriptionElement = document.getElementById(descriptionId);
  }

  renderQuestionnaireDetails(questionnaire) {
    this.titleElement.textContent = questionnaire.title;
    this.descriptionElement.textContent = questionnaire.description;
  }

  renderQuestions(questions) {
    this.questionsContainer.innerHTML = ""; // Clear previous content

    const categories = {
      Function: [],
      Appearance: [],
      Symptoms: [],
      Emotion: [],
      "Quality of Life": [],
    };

    // Group questions by category
    questions.forEach((question) => {
      if (categories[question.questionCategory]) {
        categories[question.questionCategory].push(question);
      }
    });

    // Render each category and its questions
    Object.keys(categories).forEach((category) => {
      if (categories[category].length > 0) {
        const categoryDiv = document.createElement("div");
        categoryDiv.className = "category-item";

        const categoryHeading = document.createElement("h3");
        categoryHeading.textContent = category;
        categoryDiv.appendChild(categoryHeading);

        categories[category].forEach((question, index) => {
          const questionDiv = document.createElement("div");
          questionDiv.className = "question-item";

          const questionLabel = document.createElement("label");
          questionLabel.textContent = `${index + 1}. ${question.questionText}`;
          questionLabel.setAttribute("for", `question_${question.questionID}`);
          questionDiv.appendChild(questionLabel);

          // Correctly handle "scale" type questions
          if (question.questionResponseType.toLowerCase() === "scale") {
            const scaleContainer = document.createElement("div");
            scaleContainer.className = "scale-container";

            let leftLabel, rightLabel, numOfRadios;
            if (category === "Quality of Life") {
              leftLabel = "Poor";
              rightLabel = "Excellent";
              numOfRadios = 10;
            } else {
              leftLabel = "Not at all";
              rightLabel = "A Lot";
              numOfRadios = 4;
            }

            const leftSpan = document.createElement("span");
            leftSpan.textContent = leftLabel;
            scaleContainer.appendChild(leftSpan);

            for (let i = 1; i <= numOfRadios; i++) {
              const radio = document.createElement("input");
              radio.type = "radio";
              radio.name = `question_${question.questionID}`;
              radio.value = i;
              radio.id = `question_${question.questionID}_${i}`;
              scaleContainer.appendChild(radio);
            }

            const rightSpan = document.createElement("span");
            rightSpan.textContent = rightLabel;
            scaleContainer.appendChild(rightSpan);

            questionDiv.appendChild(scaleContainer);
          } else if (question.questionResponseType.toLowerCase() === "text") {
            const textInput = document.createElement("input");
            textInput.type = "text";
            textInput.name = `question_${question.questionID}`;
            textInput.id = `question_${question.questionID}`;
            questionDiv.appendChild(textInput);
          }

          categoryDiv.appendChild(questionDiv);
        });

        this.questionsContainer.appendChild(categoryDiv);
      }
    });
  }

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
        card.className = "questionnaire-item";
        card.innerHTML = `
          <p>You have an incomplete questionnaire. Click <a href="/questionnaire/${q.questionnaire.id}">here</a> to complete it.</p>
        `;
        container.appendChild(card);
      });
    }
  }
}
