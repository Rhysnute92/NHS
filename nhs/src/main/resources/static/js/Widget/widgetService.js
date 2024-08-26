export class WidgetService {
  static async fetchUserWidgets() {
    const response = await fetch(`/api/user-widgets`);

    if (!response.ok) {
      throw new Error("Failed to fetch user widgets");
    }
    return response.json();
  }

  static async fetchWidgetFragment(widgetName) {
    const response = await fetch(`/api/widgets/${widgetName}`);

    if (!response.ok) {
      throw new Error("Widget not found");
    }
    return response.text();
  }
}
