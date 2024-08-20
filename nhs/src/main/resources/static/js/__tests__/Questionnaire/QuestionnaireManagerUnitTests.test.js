import { QuestionnaireManager } from "../../Questionnaire/QuestionnaireManager";
import { fetchData, postData, putData } from "../../common/utils/apiUtility";
import { QuestionnaireRenderer } from "../../Questionnaire/QuestionnaireRenderer";

jest.mock("../../common/utils/apiUtility");
jest.mock("../../Questionnaire/QuestionnaireRenderer");

describe("QuestionnaireManager", () => {
  let manager;
  let containerId = "test-container";
  let noAssignmentId = "test-no-assignment";
  let userID = 123;

  /**
   * Setup before each test.
   * This initializes the DOM and the QuestionnaireManager instance.
   */
  beforeEach(() => {
    document.body.innerHTML = `
      <div id="${containerId}"></div>
      <div id="${noAssignmentId}"></div>
      <form id="questionnaire-form"></form>
    `;
    manager = new QuestionnaireManager(containerId, noAssignmentId, userID);
  });

  /**
   * Clean up after each test.
   * Resets all mocks to avoid interference between tests.
   */
  afterEach(() => {
    jest.clearAllMocks();
  });

  /**
   * Test: Ensure that the QuestionnaireManager is initialized correctly
   * with the provided userID, containerId, and noAssignmentId.
   */
  test("should initialize with correct userID and setup event listener", () => {
    expect(manager.userID).toBe(userID);
    expect(manager.containerId).toBe(containerId);
    expect(manager.noAssignmentId).toBe(noAssignmentId);
    expect(QuestionnaireRenderer).toHaveBeenCalledWith(
      "questions-container",
      "questionnaire-title",
      "questionnaire-description"
    );
  });

  /**
   * Test: Verify that assigned questionnaires are fetched and rendered correctly.
   */
  test("should fetch and render assigned questionnaires", async () => {
    const mockQuestionnaires = [{ id: 1, title: "Test Questionnaire" }];
    fetchData.mockResolvedValue(mockQuestionnaires);

    await manager.loadAssignedQuestionnaires();

    expect(fetchData).toHaveBeenCalledWith(
      "/api/userQuestionnaires/user/incomplete"
    );
    expect(manager.renderer.renderAssignedQuestionnaires).toHaveBeenCalledWith(
      mockQuestionnaires,
      containerId,
      noAssignmentId
    );
  });

  /**
   * Test: Ensure that errors during fetching assigned questionnaires are handled.
   */
  test("should handle error when fetching assigned questionnaires", async () => {
    const consoleErrorSpy = jest
      .spyOn(console, "error")
      .mockImplementation(() => {});
    fetchData.mockRejectedValue(new Error("Failed to fetch data"));

    await manager.loadAssignedQuestionnaires();

    expect(consoleErrorSpy).toHaveBeenCalledWith(
      "Error loading assigned questionnaires:",
      expect.any(Error)
    );
    consoleErrorSpy.mockRestore();
  });

  /**
   * Test: Verify that a specific questionnaire and its questions are fetched and rendered correctly.
   */
  test("should fetch and render a specific questionnaire", async () => {
    const mockQuestionnaire = { id: 1, title: "Test Questionnaire" };
    const mockQuestions = [{ id: 1, text: "Question 1" }];
    fetchData.mockResolvedValueOnce(mockQuestionnaire);
    fetchData.mockResolvedValueOnce(mockQuestions);

    await manager.loadQuestionnaire(1);

    expect(fetchData).toHaveBeenCalledWith("/api/questionnaires/1");
    expect(fetchData).toHaveBeenCalledWith("/api/questions/questionnaire/1");
    expect(manager.renderer.renderQuestionnaireDetails).toHaveBeenCalledWith(
      mockQuestionnaire
    );
    expect(manager.renderer.renderQuestions).toHaveBeenCalledWith(
      mockQuestions
    );
  });

  /**
   * Test: Ensure that errors during fetching a specific questionnaire are handled.
   */
  test("should handle error when fetching a specific questionnaire", async () => {
    const consoleErrorSpy = jest
      .spyOn(console, "error")
      .mockImplementation(() => {});
    fetchData.mockRejectedValue(new Error("Failed to fetch data"));

    await manager.loadQuestionnaire(1);

    expect(consoleErrorSpy).toHaveBeenCalledWith(
      "Error loading questionnaire:",
      expect.any(Error)
    );
    consoleErrorSpy.mockRestore();
  });

  /**
   * Test: Verify that questionnaire progress is updated correctly.
   */
  test("should update questionnaire progress", async () => {
    putData.mockResolvedValue({ success: true });

    await manager.updateUserQuestionnaireProgress(1);

    expect(putData).toHaveBeenCalledWith("/api/userQuestionnaires/1", {
      questionnaireInProgress: true,
      questionnaireStartDate: expect.any(String),
    });
  });

  /**
   * Test: Ensure that errors during updating questionnaire progress are handled.
   */
  test("should handle error when updating questionnaire progress", async () => {
    const consoleErrorSpy = jest
      .spyOn(console, "error")
      .mockImplementation(() => {});
    putData.mockRejectedValue(new Error("Failed to update data"));

    await manager.updateUserQuestionnaireProgress(1);

    expect(consoleErrorSpy).toHaveBeenCalledWith(
      "Error updating user questionnaire progress:",
      expect.any(Error)
    );
    consoleErrorSpy.mockRestore();
  });

  /**
   * Test: Verify that questionnaire data is saved correctly.
   */
  test("should save questionnaire data", async () => {
    postData.mockResolvedValue({ success: true });
    const formData = new FormData();
    formData.append("question_1", "Answer 1");

    await manager.saveQuestionnaire(formData, 1);

    expect(postData).toHaveBeenCalledWith("/api/userQuestionnaires/save/1", {
      1: "Answer 1",
    });
  });

  /**
   * Test: Ensure that errors during saving questionnaire data are handled.
   */
  test("should handle error when saving questionnaire data", async () => {
    const consoleErrorSpy = jest
      .spyOn(console, "error")
      .mockImplementation(() => {});
    postData.mockRejectedValue(new Error("Failed to save data"));
    const formData = new FormData();

    await manager.saveQuestionnaire(formData, 1);

    expect(consoleErrorSpy).toHaveBeenCalledWith(
      "Error saving form data:",
      expect.any(Error)
    );
    consoleErrorSpy.mockRestore();
  });

  /**
   * Test: Verify that the questionnaire is submitted if valid.
   */
  test("should submit questionnaire if valid", async () => {
    postData.mockResolvedValueOnce({}); // Mock the API response
    delete window.location; // Mock the window location
    window.location = { href: "" }; // Initialize the mock location

    document.body.innerHTML = `
        <div id="questions-container"></div>
        <div id="no-assignment"></div>
        <form id="questionnaire-form" data-questionnaire-id="1"></form>
    `;

    const manager = new QuestionnaireManager(
      "questions-container",
      "no-assignment",
      123
    );

    const form = document.getElementById("questionnaire-form");
    const event = new Event("submit", { bubbles: true, cancelable: true });

    // Manually attach the event listener to the form
    form.addEventListener("submit", (event) =>
      manager.submitQuestionnaire(event)
    );

    // Dispatch the event and await the function
    await form.dispatchEvent(event);

    // Verify the mocked API call
    expect(postData).toHaveBeenCalledWith("/api/userQuestions/submit/1", {});

    // Verify the redirect
    expect(window.location.href).toBe("/questionnaire/1/details");
  });

  /**
   * Test: Ensure that the questionnaire is not submitted if invalid.
   */
  test("should not submit questionnaire if invalid", async () => {
    // Mock postData but ensure it's not called
    const postDataMock = postData.mockResolvedValueOnce({});
    window.alert = jest.fn(); // Mock window alert

    // Set up the form in the DOM with invalid data (empty input)
    document.body.innerHTML = `
        <div id="questions-container"></div>
        <div id="no-assignment"></div>
        <form id="questionnaire-form" data-questionnaire-id="1">
            <div class="question-item">
                <input type="text" name="question_1" value="" /> <!-- Empty value makes it invalid -->
            </div>
        </form>
    `;

    const manager = new QuestionnaireManager(
      "questions-container",
      "no-assignment",
      123
    );

    const form = document.getElementById("questionnaire-form");
    const event = new Event("submit", { bubbles: true, cancelable: true });

    // Attach the event listener to simulate form submission
    form.addEventListener("submit", (event) => manager.submitQuestionnaire(event));
    
    // Dispatch the event
    await form.dispatchEvent(event);

    // The mock should not have been called because the form is invalid
    expect(postDataMock).not.toHaveBeenCalled();
    expect(window.alert).toHaveBeenCalledWith(
      "Please answer all questions before submitting the questionnaire."
    );
});

  /**
   * Test: Validate that the form is correctly validated when all fields are filled.
   */
  test("should validate form correctly", () => {
    document.body.innerHTML = `
        <form id="questionnaire-form">
            <div class="question-item">
                <input type="text" name="question_1" value="Valid Answer" />
            </div>
        </form>
    `;

    const form = document.getElementById("questionnaire-form");
    const manager = new QuestionnaireManager(
      "questions-container",
      "no-assignment",
      123
    );
    const isValid = manager.validateForm(form);

    expect(isValid).toBe(true);
  });

  /**
   * Test: Validate that the form is marked invalid when required answers are missing.
   */
  test("should invalidate form if missing required answers", () => {
    document.body.innerHTML = `
        <form id="questionnaire-form">
            <div class="question-item">
                <input type="text" name="question_1" value="" />
            </div>
        </form>
    `;

    const form = document.getElementById("questionnaire-form");
    const manager = new QuestionnaireManager(
      "questions-container",
      "no-assignment",
      123
    );
    const isValid = manager.validateForm(form);

    expect(isValid).toBe(false);
  });

  /**
   * Test: Validate that the form is marked invalid when there are missing required answers.
   * This test is similar to the one above but demonstrates another case.
   */
  test("should invalidate form if missing required answers", () => {
    document.body.innerHTML = `
        <form id="questionnaire-form">
            <div class="question-item">
                <input type="text" name="question_1" value="" />
            </div>
        </form>
    `;

    const form = document.getElementById("questionnaire-form");
    const manager = new QuestionnaireManager(
      "questions-container",
      "no-assignment",
      123
    );

    const isValid = manager.validateForm(form);

    expect(isValid).toBe(false);
  });
});
