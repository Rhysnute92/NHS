import { WidgetManager } from "../../../../../../../../../../main/resources/static/js/Widget/widgetManager.js";
import { WidgetService } from "../../../../../../../../../../main/resources/static/js/Widget/widgetService.js";

// Mock WidgetService
jest.mock(
  "../../../../../../../../../../main/resources/static/js/Widget/widgetService.js"
);

describe("WidgetManager Integration Tests", () => {
  let widgetManager;
  let mockUserWidgets;

  beforeEach(() => {
    // Mock data
    mockUserWidgets = [
      { widgetName: "task-completion" },
      { widgetName: "appointments-tracker" },
    ];

    WidgetService.fetchUserWidgets.mockResolvedValue(mockUserWidgets);
    WidgetService.fetchWidgetFragment.mockImplementation((widgetName) => {
      if (widgetName === "task-completion") {
        return Promise.resolve("<div>Task Completion Widget</div>");
      } else if (widgetName === "appointments-tracker") {
        return Promise.resolve("<div>Appointments Tracker Widget</div>");
      }
      return Promise.reject("Unknown widget");
    });

    widgetManager = new WidgetManager(mockUserWidgets);
    document.body.innerHTML = '<div id="widget-container"></div>';
  });

  afterEach(() => {
    jest.clearAllMocks();
  });

  test("should set up widget placeholders and immediately fetch widgets", async () => {
    await widgetManager.setupWidgetPlaceholders();

    const placeholders = document.querySelectorAll(".widget-placeholder");
    expect(placeholders.length).toBe(mockUserWidgets.length);
    expect(placeholders[0].dataset.widgetName).toBe("task-completion");
    expect(placeholders[1].dataset.widgetName).toBe("appointments-tracker");

    // Verify that the widgets were immediately fetched and populated
    expect(WidgetService.fetchWidgetFragment).toHaveBeenCalledWith(
      "task-completion"
    );
    expect(WidgetService.fetchWidgetFragment).toHaveBeenCalledWith(
      "appointments-tracker"
    );
    expect(placeholders[0].innerHTML).toContain("Task Completion Widget");
    expect(placeholders[1].innerHTML).toContain("Appointments Tracker Widget");
  });

  test("should not fetch a widget that has already been fetched", async () => {
    await widgetManager.setupWidgetPlaceholders();
    const placeholders = document.querySelectorAll(".widget-placeholder");

    expect(WidgetService.fetchWidgetFragment).toHaveBeenCalledTimes(2); // Both widgets fetched

    // Simulate another attempt to fetch widgets
    await widgetManager.renderAllUserWidgets();

    // Should not re-fetch already fetched widgets
    expect(WidgetService.fetchWidgetFragment).toHaveBeenCalledTimes(2);
    expect(widgetManager.fetchedWidgets.size).toBe(2); // Ensure both widgets are marked as fetched
  });

  test("should activate widget functionality after fetching", async () => {
    const mockActivateWidgetFunctionality = jest.spyOn(
      widgetManager,
      "activateWidgetFunctionality"
    );

    await widgetManager.setupWidgetPlaceholders();

    expect(mockActivateWidgetFunctionality).toHaveBeenCalledWith(
      "task-completion"
    );
    expect(mockActivateWidgetFunctionality).toHaveBeenCalledWith(
      "appointments-tracker"
    );
  });

  test("should render all user widgets correctly with renderAllUserWidgets", async () => {
    await widgetManager.renderAllUserWidgets();

    const placeholders = document.querySelectorAll(".widget-placeholder");
    expect(placeholders.length).toBe(2);

    expect(WidgetService.fetchWidgetFragment).toHaveBeenCalledWith(
      "task-completion"
    );
    expect(WidgetService.fetchWidgetFragment).toHaveBeenCalledWith(
      "appointments-tracker"
    );
    expect(widgetManager.renderedWidgets.size).toBe(2);
  });

  test("should handle failures in fetching widget fragments", async () => {
    WidgetService.fetchWidgetFragment.mockRejectedValueOnce(
      new Error("Widget not found")
    );

    await widgetManager.renderAllUserWidgets();
    const placeholders = document.querySelectorAll(".widget-placeholder");

    // Ensure that a failed fetch does not populate the placeholder
    expect(WidgetService.fetchWidgetFragment).toHaveBeenCalledWith(
      "task-completion"
    );
    expect(placeholders[0].innerHTML).not.toContain("Task Completion Widget");
    expect(widgetManager.fetchedWidgets.has("task-completion")).toBe(false);
  });
});
