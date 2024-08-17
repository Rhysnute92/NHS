//widgetService.js

export class WidgetService {
  static async fetchUserWidgets(userId) {
    const response = await fetch(`/api/user-widgets/${userId}`);
    console.log("Fetched user widgets response:", response);
    if (!response.ok) {
      throw new Error("Failed to fetch user widgets");
    }
    return response.json();
  }

  static async fetchWidgetFragment(widgetName) {
    const response = await fetch(`/api/widgets/${widgetName}`);
    console.log(`Fetched widget (${widgetName}) response:`, response);
    if (!response.ok) {
      throw new Error("Widget not found");
    }
    return response.text();
  }

  static async fetchWidgetScript(widgetName) {
    const response = await fetch(`/api/widgets/${widgetName}/script`);
    console.log(`Fetched widget (${widgetName}) script response:`, response);
    if (response.ok) {
      return response.text();
    }
    return null;
  }
}
