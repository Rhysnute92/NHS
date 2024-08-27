import { WidgetService } from "../../../../../../../../../../main/resources/static/js/Widget/widgetService.js";

describe("WidgetService", () => {
  let originalConsoleLog;

  beforeEach(() => {
    global.fetch = jest.fn();
    // Store the original console.log
    originalConsoleLog = console.log;
    // Replace console.log with a no-op function
    console.log = jest.fn();
  });

  afterEach(() => {
    // Restore the original console.log after each test
    console.log = originalConsoleLog;
  });

  test("fetchUserWidgets returns user widgets on successful fetch", async () => {
    const mockWidgets = [{ widgetName: "test-widget" }];
    global.fetch.mockResolvedValueOnce({
      ok: true,
      json: () => Promise.resolve(mockWidgets),
    });

    const result = await WidgetService.fetchUserWidgets();
    expect(result).toEqual(mockWidgets);
    expect(fetch).toHaveBeenCalledWith("/api/user-widgets");
  });

  test("fetchUserWidgets throws error on failed fetch", async () => {
    global.fetch.mockResolvedValueOnce({
      ok: false,
    });

    await expect(WidgetService.fetchUserWidgets()).rejects.toThrow(
      "Failed to fetch user widgets"
    );
  });

  test("fetchWidgetFragment returns widget content on successful fetch", async () => {
    const mockWidgetContent = "<div>Widget Content</div>";
    global.fetch.mockResolvedValueOnce({
      ok: true,
      text: () => Promise.resolve(mockWidgetContent),
    });

    const result = await WidgetService.fetchWidgetFragment("test-widget");
    expect(result).toEqual(mockWidgetContent);
    expect(fetch).toHaveBeenCalledWith("/api/widgets/test-widget");
  });

  test("fetchWidgetFragment throws error on failed fetch", async () => {
    global.fetch.mockResolvedValueOnce({
      ok: false,
    });

    await expect(
      WidgetService.fetchWidgetFragment("test-widget")
    ).rejects.toThrow("Widget not found");
  });
});
