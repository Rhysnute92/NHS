import { WidgetManager } from "../../../../../../../../../../main/resources/static/js/Widget/widgetManager.js";
import { WidgetService } from "../../../../../../../../../../main/resources/static/js/Widget/widgetService.js";

// Mock the WidgetService methods
jest.mock(
  "../../../../../../../../../../main/resources/static/js/Widget/widgetService.js"
);

describe("WidgetManager Integration Tests", () => {
  let widgetManager;
  let mockUserWidgets;
  let mockImportFunction;

  beforeEach(() => {
    // Sample widget data
    mockUserWidgets = [
      { widgetName: "task-completion" },
      { widgetName: "test-widget" }, // Generic widget for testing
    ];

    // Mock dynamic import function
    mockImportFunction = jest.fn((path) => {
      if (path.includes("task-completion")) {
        return Promise.resolve({
          default: class {
            async updateWidgetData() {}
          },
        });
      } else if (path.includes("test-widget")) {
        return Promise.resolve({
          default: class {
            async updateWidgetData() {}
          },
        });
      }
      return Promise.reject(new Error("Module not found"));
    });

    // Set up the WidgetService mocks
    WidgetService.fetchUserWidgets.mockResolvedValue(mockUserWidgets);
    WidgetService.fetchWidgetFragment.mockImplementation((widgetName) => {
      if (widgetName === "task-completion") {
        return Promise.resolve("<div>Task Completion Widget</div>");
      } else if (widgetName === "test-widget") {
        return Promise.resolve("<div>Test Widget</div>");
      }
      return Promise.reject(new Error("Widget not found"));
    });

    // Create an instance of WidgetManager
    widgetManager = new WidgetManager(mockUserWidgets, mockImportFunction);

    // Reset the DOM container for each test
    document.body.innerHTML = '<div id="widget-container"></div>';
  });

  afterEach(() => {
    // Clear all mocks after each test to prevent state leakage
    jest.clearAllMocks();
  });

  test("should set up widget placeholders", () => {
    // Act
    widgetManager.setupWidgetPlaceholders();

    // Assert placeholders are set up correctly
    const placeholders = document.querySelectorAll(".widget-placeholder");
    expect(placeholders.length).toBe(mockUserWidgets.length);
    expect(placeholders[0].dataset.widgetName).toBe("task-completion");
    expect(placeholders[1].dataset.widgetName).toBe("test-widget");
  });

  test("should fetch and populate the widgets", async () => {
    // Act
    widgetManager.setupWidgetPlaceholders();
    await widgetManager.renderAllUserWidgets();

    // Assert widgets are fetched and populated
    const placeholders = document.querySelectorAll(".widget-placeholder");
    expect(WidgetService.fetchWidgetFragment).toHaveBeenCalledWith(
      "task-completion"
    );
    expect(WidgetService.fetchWidgetFragment).toHaveBeenCalledWith(
      "test-widget"
    );
    expect(placeholders[0].innerHTML).toContain("Task Completion Widget");
    expect(placeholders[1].innerHTML).toContain("Test Widget");
  });

  test("should activate widget functionality after fetching", async () => {
    // Spy on activateWidgetFunctionality
    const mockActivateWidgetFunctionality = jest.spyOn(
      widgetManager,
      "activateWidgetFunctionality"
    );

    // Act
    widgetManager.setupWidgetPlaceholders();
    await widgetManager.renderAllUserWidgets();

    // Assert the functionality is activated for each widget
    expect(mockActivateWidgetFunctionality).toHaveBeenCalledWith(
      "task-completion"
    );
    expect(mockActivateWidgetFunctionality).toHaveBeenCalledWith("test-widget");
  });
  /* 
  test("should handle failures in fetching a widget fragment", async () => {
    // Set up mock to fail for test-widget
    WidgetService.fetchWidgetFragment.mockImplementationOnce((widgetName) => {
      if (widgetName === "test-widget") {
        return Promise.reject(new Error("Widget not found"));
      }
      return Promise.resolve("<div>Task Completion Widget</div>");
    });

    // Act
    widgetManager.setupWidgetPlaceholders();
    await widgetManager.renderAllUserWidgets();

    // Assert the test-widget fails gracefully
    const placeholders = document.querySelectorAll(".widget-placeholder");
    expect(WidgetService.fetchWidgetFragment).toHaveBeenCalledWith(
      "test-widget"
    );
    // Placeholder should still contain the loading text since the fetch failed
    expect(placeholders[1].innerHTML).toContain(
      "Loading test-widget widget..."
    );
    expect(widgetManager.fetchedWidgets.has("test-widget")).toBe(false);
  });

  test("should handle cases where a widget's module cannot be imported", async () => {
    // Set up mock to fail for test-widget
    mockImportFunction.mockImplementationOnce((path) => {
      if (path.includes("test-widget")) {
        return Promise.reject(new Error("Module not found"));
      }
      return Promise.resolve({
        default: class {
          async updateWidgetData() {}
        },
      });
    });

    // Spy on console.error for error handling
    const consoleErrorSpy = jest
      .spyOn(console, "error")
      .mockImplementation(() => {});

    // Act
    widgetManager.setupWidgetPlaceholders();
    await widgetManager.renderAllUserWidgets();

    // Assert error handling for module import failure
    expect(consoleErrorSpy).toHaveBeenCalledWith(
      `Failed to activate functionality for widget test-widget:`,
      expect.any(Error)
    );
    expect(mockImportFunction).toHaveBeenCalledWith(
      "../widgets/test-widgetWidget.js"
    );

    // Cleanup
    consoleErrorSpy.mockRestore();
  }); */
});
