/**
 * Fetch the full name of the currently authenticated user.
 * @returns {Promise<string>} The full name of the user.
 */
export async function fetchUserFullName() {
  const endpoint = "/userFullName";
  try {
    const response = await fetch(endpoint);
    if (!response.ok) {
      throw new Error("Network response was not ok");
    }
    const fullName = await response.text();
    return fullName;
  } catch (error) {
    console.error("Error fetching user full name:", error);
    throw error; // Re-throw error for caller to handle
  }
}

/**
 * Fetch the user ID of the currently authenticated user.
 * @returns {Promise<number>} The user ID.
 */
export async function fetchUserID() {
  const endpoint = "/userID";
  try {
    const response = await fetch(endpoint);
    if (!response.ok) {
      throw new Error("Network response was not ok");
    }
    const userId = await response.json();
    return userId;
  } catch (error) {
    console.error("Error fetching user ID:", error);
    throw error; // Re-throw error for caller to handle
  }
}
