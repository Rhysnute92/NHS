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

  static async removeUserWidgets(widgetIds) {
    const response = await fetch(`/api/user-widgets/delete`, {
      method: "DELETE",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(widgetIds), // Correctly formatted to just send the array
    });

    if (!response.ok) {
      throw new Error("Failed to remove widgets");
    }

    // Only parse the response as JSON if the response is not 204 No Content
    if (response.status !== 204) {
      return response.json();
    } else {
      return {}; // Return an empty object or another value to indicate success
    }
  }
}
