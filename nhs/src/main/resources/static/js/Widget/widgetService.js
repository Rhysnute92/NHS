export class WidgetService {
  static async fetchUserWidgets() {
    const logId = Math.random().toString(36).substring(2, 10);
    const timestamp = new Date().toISOString();

    const response = await fetch(`/api/user-widgets`);
    console.log(
      `[${timestamp}] [${logId}] Fetched user widgets response:`,
      response
    );

    if (!response.ok) {
      throw new Error("Failed to fetch user widgets");
    }
    return response.json();
  }

  static async fetchWidgetFragment(widgetName) {
    const logId = Math.random().toString(36).substring(2, 10);
    const timestamp = new Date().toISOString();

    console.log(
      `[${timestamp}] [${logId}] Fetching widget fragment for: ${widgetName}`
    );
    const response = await fetch(`/api/widgets/${widgetName}`);
    console.log(
      `[${timestamp}] [${logId}] Fetched widget (${widgetName}) response:`,
      response
    );

    if (!response.ok) {
      throw new Error("Widget not found");
    }
    return response.text();
  }
}
