export class WidgetService {
  static async fetchUserWidgets() {
    const response = await fetch(`/api/user-widgets`);

    if (!response.ok) {
      throw new Error("Failed to fetch user widgets");
    }
    return response.json();
  }

  static async fetchAvailableWidgets() {
    const response = await fetch(`/api/widgets/available`);

    if (!response.ok) {
      throw new Error("Failed to fetch available widgets");
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
      body: JSON.stringify(widgetIds),
    });

    if (!response.ok) {
      throw new Error("Failed to remove widgets");
    }

    if (response.status !== 204) {
      return response.json();
    } else {
      return {};
    }
  }

  static async addUserWidgets(widgetNames) {
    const response = await fetch(`/api/user-widgets/add`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(widgetNames),
    });

    if (!response.ok) {
      throw new Error("Failed to add widgets");
    }

    if (response.status !== 204) {
      return response.json();
    } else {
      return {};
    }
  }
}
