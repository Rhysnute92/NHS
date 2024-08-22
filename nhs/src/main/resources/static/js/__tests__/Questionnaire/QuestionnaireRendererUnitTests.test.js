import { QuestionnaireRenderer } from "../../Questionnaire/QuestionnaireRenderer";

describe("QuestionnaireRenderer", () => {
  let renderer;
  let questionsContainerId = "questions-container";
  let titleId = "questionnaire-title";
  let descriptionId = "questionnaire-description";

  /**
   * Setup before each test.
   * This initializes the DOM and the QuestionnaireRenderer instance.
   */
  beforeEach(() => {
    document.body.innerHTML = `
      <div id="${questionsContainerId}"></div>
      <h1 id="${titleId}"></h1>
      <p id="${descriptionId}"></p>
    `;
    renderer = new QuestionnaireRenderer(
      questionsContainerId,
      titleId,
      descriptionId
    );
  });

  /**
   * Clean up after each test.
   * Resets the DOM.
   */
  afterEach(() => {
    document.body.innerHTML = "";
  });

  test("should initialize with correct DOM elements", () => {
    expect(renderer.questionsContainer).toBeInstanceOf(HTMLElement);
    expect(renderer.titleElement).toBeInstanceOf(HTMLElement);
    expect(renderer.descriptionElement).toBeInstanceOf(HTMLElement);
  });

  test("should render questionnaire details correctly", () => {
    const mockQuestionnaire = {
      title: "Test Title",
      description: "Test Description",
    };
    renderer.renderQuestionnaireDetails(mockQuestionnaire);

    expect(renderer.titleElement.textContent).toBe(mockQuestionnaire.title);
    expect(renderer.descriptionElement.textContent).toBe(
      mockQuestionnaire.description
    );
  });

  test("should clear the questions container", () => {
    document.getElementById(questionsContainerId).innerHTML =
      "<p>Old Content</p>";
    renderer.clearContainer();
    expect(renderer.questionsContainer.innerHTML).toBe("");
  });

  test("should categorize questions correctly", () => {
    const mockQuestions = [
      { questionCategory: "Function", questionText: "Q1" },
      { questionCategory: "Appearance", questionText: "Q2" },
      { questionCategory: "Symptoms", questionText: "Q3" },
      { questionCategory: "Emotion", questionText: "Q4" },
      { questionCategory: "Quality of Life", questionText: "Q5" },
      { questionCategory: "Unknown", questionText: "Q6" }, // Unrecognized category
    ];

    const categories = renderer.categorizeQuestions(mockQuestions);

    expect(categories.Function.length).toBe(1);
    expect(categories.Appearance.length).toBe(1);
    expect(categories.Symptoms.length).toBe(1);
    expect(categories.Emotion.length).toBe(1);
    expect(categories["Quality of Life"].length).toBe(1);
    expect(categories.Unknown).toBeUndefined(); // Unknown category should not be recognized
  });

  test("should render questions and categories", () => {
    const mockQuestions = [
      {
        questionCategory: "Function",
        questionText: "Q1",
        questionResponseType: "scale",
        questionID: 1,
      },
      {
        questionCategory: "Appearance",
        questionText: "Q2",
        questionResponseType: "text",
        questionID: 2,
      },
    ];

    renderer.renderQuestions(mockQuestions);

    const renderedQuestions = document.querySelectorAll(".question-item");
    expect(renderedQuestions.length).toBe(2);
  });

  test("should create category div correctly", () => {
    const categoryDiv = renderer.createCategoryDiv("Function");
    expect(categoryDiv).toBeInstanceOf(HTMLElement);
    expect(categoryDiv.querySelector("h3").textContent).toBe("Function");
  });

  test("should create question div with scale inputs", () => {
    const mockQuestion = {
      questionID: 1,
      questionText: "How do you feel?",
      questionResponseType: "scale",
    };
    const questionDiv = renderer.createQuestionDiv(mockQuestion, 0, "Function");

    expect(questionDiv).toBeInstanceOf(HTMLElement);
    expect(questionDiv.querySelectorAll("input[type='radio']").length).toBe(4); // Default scale is 4 radios
  });

  test("should create question div with text input", () => {
    const mockQuestion = {
      questionID: 2,
      questionText: "Describe your symptoms.",
      questionResponseType: "text",
    };
    const questionDiv = renderer.createQuestionDiv(mockQuestion, 1, "Symptoms");

    expect(questionDiv).toBeInstanceOf(HTMLElement);
    expect(questionDiv.querySelector("input[type='text']")).toBeTruthy();
  });

  test("should render assigned questionnaires correctly", () => {
    document.body.innerHTML += `
      <div id="assigned-container"></div>
      <div id="no-assignment" style="display: none;"></div>
    `;

    const mockQuestionnaires = [
      { questionnaire: { id: 1, title: "Q1" } },
      { questionnaire: { id: 2, title: "Q2" } },
    ];

    renderer.renderAssignedQuestionnaires(
      mockQuestionnaires,
      "assigned-container",
      "no-assignment"
    );

    const renderedQuestionnaires = document.querySelectorAll(
      ".questionnaire-card"
    );
    expect(renderedQuestionnaires.length).toBe(2);
    expect(document.getElementById("no-assignment").style.display).toBe("none");
  });

  test("should show no assignment message if there are no assigned questionnaires", () => {
    document.body.innerHTML += `
      <div id="assigned-container"></div>
      <div id="no-assignment" style="display: none;"></div>
    `;

    renderer.renderAssignedQuestionnaires(
      [],
      "assigned-container",
      "no-assignment"
    );

    const noAssignmentElement = document.getElementById("no-assignment");
    expect(noAssignmentElement.style.display).toBe("block");
    expect(document.getElementById("assigned-container").children.length).toBe(
      0
    );
  });
});
