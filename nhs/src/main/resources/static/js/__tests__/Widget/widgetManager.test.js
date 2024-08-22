import { WidgetManager } from "../../Widget/widgetManager.js";

// Mock IntersectionObserver
global.IntersectionObserver = class IntersectionObserver {
  constructor() {}
  observe() {}
  unobserve() {}
  disconnect() {}
};

describe("WidgetManager", () => {
  let widgetManager;
  let mockUserWidgets;

  beforeEach(() => {
    mockUserWidgets = [
      { widgetName: "task-completion" },
      { widgetName: "appointments-tracker" },
    ];
    widgetManager = new WidgetManager(mockUserWidgets);
    document.body.innerHTML = '<div id="widget-container"></div>';
  });

  test("initializeUserWidgets creates placeholders for all user widgets", () => {
    widgetManager.initializeUserWidgets();
    const placeholders = document.querySelectorAll(".widget-placeholder");
    expect(placeholders.length).toBe(mockUserWidgets.length);
  });

  test("createWidgetPlaceholder creates a placeholder with correct attributes", () => {
    const placeholder = widgetManager.createWidgetPlaceholder("test-widget");
    expect(placeholder.className).toBe("widget-placeholder");
    expect(placeholder.dataset.widgetName).toBe("test-widget");
    expect(placeholder.textContent).toContain("Loading test-widget widget");
  });

  test("loadWidget fetches and inserts widget content", async () => {
    global.fetch = jest.fn(() =>
      Promise.resolve({
        ok: true,
        text: () => Promise.resolve("<div>Widget Content</div>"),
      })
    );

    const placeholder = document.createElement("div");
    await widgetManager.loadWidget("test-widget", placeholder);

    expect(fetch).toHaveBeenCalledWith("/api/widgets/test-widget");
    expect(placeholder.innerHTML).toBe("<div>Widget Content</div>");
  });
});
