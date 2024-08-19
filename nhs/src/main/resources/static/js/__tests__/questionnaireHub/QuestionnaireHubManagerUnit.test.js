import { fetchData } from "../../common/utils/apiUtility.js";
import { QuestionnaireHubManager } from "../../Questionnaire/QuestionnaireHub/questionaireHubManager";
import { QuestionnaireHubRendererProviderView } from "../../Questionnaire/QuestionnaireHub/questionnaireHubRenderer";

// Mock the necessary imports
jest.mock("../../common/utils/apiUtility");
jest.mock("../../Questionnaire/QuestionnaireHub/questionnaireHubRenderer");

describe("QuestionnaireHubManager", () => {
  let manager;
  let mockRenderer;

  beforeEach(() => {
    mockRenderer = new QuestionnaireHubRendererProviderView();
    manager = new QuestionnaireHubManager("containerId", "noAssignmentId");
    manager.renderer = mockRenderer; // Replace the actual renderer with the mock
  });

  test("should fetch and render assigned questionnaires", async () => {
    const mockData = [{ questionnaire: { title: "Test Questionnaire" } }];
    fetchData.mockResolvedValue(mockData);
    mockRenderer.renderAssignedQuestionnaires = jest.fn();

    await manager.loadAssignedQuestionnaires(1);

    expect(fetchData).toHaveBeenCalledWith("/api/provider/incomplete/1");
    expect(mockRenderer.renderAssignedQuestionnaires).toHaveBeenCalledWith(
      mockData,
      "containerId",
      "noAssignmentId"
    );
  });

  test("should handle errors while fetching questionnaires", async () => {
    fetchData.mockRejectedValue(new Error("Fetch error"));
    console.error = jest.fn(); // Mock console.error

    await manager.loadAssignedQuestionnaires(1);

    expect(console.error).toHaveBeenCalledWith(
      "Error loading assigned questionnaires:",
      expect.any(Error)
    );
  });
});
