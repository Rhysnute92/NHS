import { WidgetService } from "../../Widget/widgetService.js";

describe("WidgetService", () => {
  beforeEach(() => {
    global.fetch = jest.fn();
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
});
