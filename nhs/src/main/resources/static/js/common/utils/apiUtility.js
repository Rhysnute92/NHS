export async function fetchData(url) {
  try {
    const response = await fetch(url);
    if (!response.ok) {
      throw new Error(`Failed to fetch data from ${url}`);
    }
    return await response.json();
  } catch (error) {
    console.error(`Error fetching data: ${error.message}`);
    throw error;
  }
}

export async function postData(url, data) {
  try {
    const response = await fetch(url, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(data),
    });
    if (!response.ok) {
      throw new Error(`Failed to post data to ${url}`);
    }
    return await response.json();
  } catch (error) {
    console.error(`Error posting data: ${error.message}`);
    throw error;
  }
}

export async function putData(url = "", data = {}) {
  try {
    const response = await fetch(url, {
      method: "PUT",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(data),
    });

    if (!response.ok) {
      throw new Error(`Failed to put data to ${url}`);
    }

    // Check if there's a response body before parsing it as JSON
    const contentType = response.headers.get("content-type");
    if (contentType && contentType.includes("application/json")) {
      return await response.json();
    } else {
      return; // No content in the response
    }
  } catch (error) {
    console.error(`Error putting data: ${error.message}`);
    throw error;
  }
}
