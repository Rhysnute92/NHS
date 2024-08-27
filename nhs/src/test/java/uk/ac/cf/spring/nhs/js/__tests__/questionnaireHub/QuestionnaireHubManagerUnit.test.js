import { fetchData } from "../../../../../../../../../../main/resources/static/js/common/utils/apiUtility";
import { QuestionnaireHubManager } from "../../../../../../../../../../main/resources/static/js/Questionnaire/QuestionnaireHub/QuestionnaireHubManager.js";
import { QuestionnaireHubRendererProviderView } from "../../../../../../../../../../main/resources/static/js/Questionnaire/QuestionnaireHub/QuestionnaireHubRendererProviderView.js";

// Mock the necessary imports
jest.mock(
  "../../../../../../../../../../main/resources/static/js/common/utils/apiUtility.js"
);
jest.mock(
  "../../../../../../../../../../main/resources/static/js/Questionnaire/QuestionnaireHub/QuestionnaireHubRendererProviderView.js"
);

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
