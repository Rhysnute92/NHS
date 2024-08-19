/* import { QuestionnaireHubManager } from "../../Questionnaire/QuestionnaireHub/questionaireHubManager";
import { QuestionnaireHubRenderer } from "../../Questionnaire/QuestionnaireHub/questionnaireHubRenderer";
import { fetchData, postData } from "../../common/utils/apiUtility";

jest.mock("../../common/utils/apiUtility");

describe("QuestionnaireHubManager", () => {
  let manager;

  beforeEach(() => {
    manager = new QuestionnaireHubManager("containerId", "noAssignmentId");
  });

  test("should fetch and render assigned questionnaires", async () => {
    const mockData = [{ questionnaire: { title: "Test Questionnaire" } }];
    fetchData.mockResolvedValue(mockData);

    await manager.loadAssignedQuestionnaires(1);

    expect(fetchData).toHaveBeenCalledWith("/api/provider/incomplete/1");
    expect(manager.renderer.renderAssignedQuestionnaires).toHaveBeenCalledWith(
      mockData,
      "containerId",
      "noAssignmentId"
    );
  });

  test("should handle errors while fetching questionnaires", async () => {
    fetchData.mockRejectedValue(new Error("Fetch error"));

    await manager.loadAssignedQuestionnaires(1);

    expect(console.error).toHaveBeenCalledWith(
      "Error loading assigned questionnaires:",
      expect.any(Error)
    );
  });
});
 */
