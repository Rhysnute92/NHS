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
    this.clearContainer();

    const categories = this.categorizeQuestions(questions);
    this.renderCategories(categories);
  }

  clearContainer() {
    this.questionsContainer.innerHTML = "";
    console.log("Cleared the questions container");
  }

  categorizeQuestions(questions) {
    const categories = {
      Function: [],
      Appearance: [],
      Symptoms: [],
      Emotion: [],
      "Quality of Life": [],
    };

    questions.forEach((question) => {
      if (categories[question.questionCategory]) {
        categories[question.questionCategory].push(question);
      } else {
        console.warn(`Unrecognized category: ${question.questionCategory}`);
      }
    });

    console.log("Categorized questions:", categories);
    return categories;
  }

  renderCategories(categories) {
    Object.keys(categories).forEach((category) => {
      if (categories[category].length > 0) {
        const categoryDiv = this.createCategoryDiv(category);
        this.renderCategoryQuestions(
          categoryDiv,
          categories[category],
          category
        );
        this.questionsContainer.appendChild(categoryDiv);
      }
    });

    console.log("Finished rendering categories and questions");
  }

  createCategoryDiv(category) {
    const categoryDiv = document.createElement("div");
    categoryDiv.className = "category-item";

    const categoryHeading = document.createElement("h3");
    categoryHeading.textContent = category;
    categoryDiv.appendChild(categoryHeading);

    console.log(`Created div for category: ${category}`);
    return categoryDiv;
  }

  renderCategoryQuestions(categoryDiv, questions, category) {
    questions.forEach((question, index) => {
      const questionDiv = this.createQuestionDiv(question, index, category);
      categoryDiv.appendChild(questionDiv);
    });

    console.log(`Rendered questions for category: ${category}`);
  }

  createQuestionDiv(question, index, category) {
    const questionDiv = document.createElement("div");
    questionDiv.className = "question-item";

    const questionLabel = document.createElement("label");
    questionLabel.textContent = `${index + 1}. ${question.questionText}`;
    questionLabel.setAttribute("for", `question_${question.questionID}`);
    questionDiv.appendChild(questionLabel);

    if (question.questionResponseType.toLowerCase() === "scale") {
      const scaleContainer = this.createScaleContainer(question, category);
      questionDiv.appendChild(scaleContainer);
    } else if (question.questionResponseType.toLowerCase() === "text") {
      const textInput = this.createTextInput(question);
      questionDiv.appendChild(textInput);
    } else {
      console.warn(
        `Unknown question response type: ${question.questionResponseType}`
      );
    }

    console.log(`Created question div for question ID: ${question.questionID}`);
    return questionDiv;
  }

  createScaleContainer(question, category) {
    const scaleContainer = document.createElement("div");
    scaleContainer.className = "scale-container";

    let leftLabel, rightLabel, numOfRadios, qolClass;
    if (category === "Quality of Life") {
      leftLabel = "Poor";
      rightLabel = "Excellent";
      numOfRadios = 10;
      qolClass = "qol"; // Class for Quality of Life
    } else {
      leftLabel = "Not at all";
      rightLabel = "A Lot";
      numOfRadios = 4;
      qolClass = ""; // No specific class
    }

    const leftSpan = document.createElement("span");
    leftSpan.textContent = leftLabel;
    scaleContainer.appendChild(leftSpan);

    for (let i = 1; i <= numOfRadios; i++) {
      const wrapper = document.createElement("div");
      wrapper.className = "radio-wrapper";

      const numberLabel = document.createElement("div");
      numberLabel.className = "number-label";
      numberLabel.textContent = i;
      wrapper.appendChild(numberLabel);

      const radio = this.createRadioInput(
        question,
        i,
        qolClass ? `${qolClass}-${i}` : `non-qol-${i}` // Differentiate between QOL and non-QOL
      );
      wrapper.appendChild(radio);

      scaleContainer.appendChild(wrapper);
    }

    const rightSpan = document.createElement("span");
    rightSpan.textContent = rightLabel;
    scaleContainer.appendChild(rightSpan);

    console.log(
      `Created scale container for question ID: ${question.questionID}`
    );
    return scaleContainer;
  }

  createRadioInput(question, index, additionalClass = "") {
    const radio = document.createElement("input");
    radio.type = "radio";
    radio.name = `question_${question.questionID}`;
    radio.value = index;
    radio.id = `question_${question.questionID}_${index}`;

    if (additionalClass) {
      radio.classList.add(additionalClass);
    }

    console.log(
      `Created radio input for question ID: ${question.questionID}, index: ${index}`
    );
    return radio;
  }

  createTextInput(question) {
    const textInput = document.createElement("input");
    textInput.type = "text";
    textInput.name = `question_${question.questionID}`;
    textInput.id = `question_${question.questionID}`;

    console.log(`Created text input for question ID: ${question.questionID}`);
    return textInput;
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
