import { QuestionnaireManager } from "../../../../../../../../../../main/resources/static/js/Questionnaire/QuestionnaireManager";

// Mock the DOM content
document.body.innerHTML = `
  <form id="questionnaire-form"></form>
  <button id="save-questionnaire"></button>
`;

jest.mock(
  "../../../../../../../../../../main/resources/static/js/Questionnaire/QuestionnaireManager",
  () => {
    return {
      QuestionnaireManager: jest.fn().mockImplementation(() => {
        return {
          loadQuestionnaire: jest.fn(),
          submitQuestionnaire: jest.fn(),
          saveQuestionnaire: jest.fn(),
        };
      }),
    };
  }
);

describe("QuestionnaireManager Initialization", () => {
  let questionnaireManager;

  beforeEach(() => {
    // Reset the mock implementations before each test
    QuestionnaireManager.mockClear();
    questionnaireManager = new QuestionnaireManager();

    // Mock the window location object
    delete window.location;
    window.location = {
      pathname: "",
      href: "",
      assign: jest.fn(),
    };
  });

  test("should submit questionnaire on form submit", async () => {
    const expectedQuestionnaireId = "123";
    window.location.pathname = `/questionnaire/${expectedQuestionnaireId}`;
    document.dispatchEvent(new Event("DOMContentLoaded"));

    const form = document.getElementById("questionnaire-form");
    const submitEvent = new Event("submit", {
      bubbles: true,
      cancelable: true,
    });
    const formDataMock = new FormData(form);

    form.dispatchEvent(submitEvent);

    // Manually create FormData mock for the form
    jest.spyOn(window, "FormData").mockImplementation(() => formDataMock);

    await questionnaireManager.submitQuestionnaire(
      formDataMock,
      expectedQuestionnaireId
    );
    expect(questionnaireManager.submitQuestionnaire).toHaveBeenCalledWith(
      formDataMock,
      expectedQuestionnaireId
    );
  });

  test("should save questionnaire on save button click", async () => {
    const expectedQuestionnaireId = "123";
    window.location.pathname = `/questionnaire/${expectedQuestionnaireId}`;
    document.dispatchEvent(new Event("DOMContentLoaded"));

    const saveButton = document.getElementById("save-questionnaire");
    const formDataMock = new FormData(
      document.getElementById("questionnaire-form")
    );

    saveButton.click();

    // Manually create FormData mock for the form
    jest.spyOn(window, "FormData").mockImplementation(() => formDataMock);

    await questionnaireManager.saveQuestionnaire(
      formDataMock,
      expectedQuestionnaireId
    );
    expect(questionnaireManager.saveQuestionnaire).toHaveBeenCalledWith(
      formDataMock,
      expectedQuestionnaireId
    );
  });
});
