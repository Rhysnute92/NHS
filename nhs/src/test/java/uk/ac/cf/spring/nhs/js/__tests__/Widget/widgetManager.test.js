import { WidgetManager } from "../../../../../../../../../../main/resources/static/js/Widget/widgetManager.js";
import { WidgetService } from "../../../../../../../../../../main/resources/static/js/Widget/widgetService.js";

// Mock IntersectionObserver
global.IntersectionObserver = class IntersectionObserver {
  constructor() {}
  observe() {}
  unobserve() {}
  disconnect() {}
};

// Mock WidgetService
jest.mock(
  "../../../../../../../../../../main/resources/static/js/Widget/widgetService.js"
);

describe("WidgetManager", () => {
  let widgetManager;
  let mockUserWidgets;

  beforeEach(() => {
    mockUserWidgets = [
      { widgetName: "task-completion" },
      { widgetName: "appointments-tracker" },
    ];
    const mockImport = jest.fn().mockResolvedValue({ default: jest.fn() });
    widgetManager = new WidgetManager(mockUserWidgets, mockImport);
    document.body.innerHTML = '<div id="widget-container"></div>';

    // Mock activateWidgetFunctionality to prevent additional calls
    widgetManager.activateWidgetFunctionality = jest.fn();
  });

  test("setupWidgetPlaceholders creates placeholders for all user widgets", () => {
    widgetManager.setupWidgetPlaceholders();
    const placeholders = document.querySelectorAll(".widget-placeholder");
    expect(placeholders.length).toBe(mockUserWidgets.length);
  });

  test("generatePlaceholderElement creates a placeholder with correct attributes", () => {
    const placeholder = widgetManager.generatePlaceholderElement("test-widget");
    expect(placeholder.className).toBe("widget-placeholder");
    expect(placeholder.dataset.widgetName).toBe("test-widget");
    expect(placeholder.textContent).toContain("Loading test-widget widget");
  });

  test("fetchAndPopulateWidget fetches and inserts widget content", async () => {
    WidgetService.fetchWidgetFragment.mockResolvedValue(
      "<div>Widget Content</div>"
    );

    const placeholder = document.createElement("div");
    await widgetManager.fetchAndPopulateWidget("test-widget", placeholder);

    expect(WidgetService.fetchWidgetFragment).toHaveBeenCalledWith(
      "test-widget"
    );
    expect(placeholder.innerHTML).toBe("<div>Widget Content</div>");
    expect(widgetManager.activateWidgetFunctionality).toHaveBeenCalledWith(
      "test-widget"
    );
  });

  test("renderAllUserWidgets renders all widgets", async () => {
    WidgetService.fetchWidgetFragment.mockImplementation((widgetName) => {
      console.log(`Fetching widget: ${widgetName}`);
      return Promise.resolve("<div>Widget Content</div>");
    });

    await widgetManager.renderAllUserWidgets();

    const renderedWidgets = document.querySelectorAll(".widget-placeholder");
    expect(renderedWidgets.length).toBe(mockUserWidgets.length);
    expect(WidgetService.fetchWidgetFragment).toHaveBeenCalledTimes(
      mockUserWidgets.length
    );

    mockUserWidgets.forEach((widget) => {
      expect(WidgetService.fetchWidgetFragment).toHaveBeenCalledWith(
        widget.widgetName
      );
    });
  });

  test("activateWidgetFunctionality dynamically imports and initializes widget", async () => {
    const mockUpdateWidgetData = jest.fn();
    const mockWidgetClass = jest.fn().mockImplementation(() => ({
      updateWidgetData: mockUpdateWidgetData,
    }));

    const mockImport = jest
      .fn()
      .mockResolvedValue({ default: mockWidgetClass });
    const testWidgetManager = new WidgetManager(mockUserWidgets, mockImport);

    await testWidgetManager.activateWidgetFunctionality("task-completion");

    expect(mockImport).toHaveBeenCalledWith(
      "../widgets/task-completionWidget.js"
    );
    expect(mockWidgetClass).toHaveBeenCalled();
    expect(mockUpdateWidgetData).toHaveBeenCalled();
  });

  test("activateWidgetFunctionality handles missing updateWidgetData method", async () => {
    const mockWidgetClass = jest.fn().mockImplementation(() => ({}));
    const mockImport = jest
      .fn()
      .mockResolvedValue({ default: mockWidgetClass });
    const testWidgetManager = new WidgetManager(mockUserWidgets, mockImport);

    const consoleSpy = jest.spyOn(console, "log");

    await testWidgetManager.activateWidgetFunctionality("test-widget");

    expect(mockImport).toHaveBeenCalledWith("../widgets/test-widgetWidget.js");
    expect(mockWidgetClass).toHaveBeenCalled();
    expect(consoleSpy).toHaveBeenCalledWith(
      "Widget test-widget does not have an updateWidgetData method"
    );

    consoleSpy.mockRestore();
  });

  test("activateWidgetFunctionality handles import errors", async () => {
    const mockImport = jest
      .fn()
      .mockRejectedValue(new Error("Module not found"));
    const testWidgetManager = new WidgetManager(mockUserWidgets, mockImport);

    const consoleErrorSpy = jest.spyOn(console, "error");

    await testWidgetManager.activateWidgetFunctionality("non-existent-widget");

    expect(mockImport).toHaveBeenCalledWith(
      "../widgets/non-existent-widgetWidget.js"
    );
    expect(consoleErrorSpy).toHaveBeenCalledWith(
      "Failed to activate functionality for widget non-existent-widget:",
      expect.any(Error)
    );

    consoleErrorSpy.mockRestore();
  });
});
