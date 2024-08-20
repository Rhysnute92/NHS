import { fetchData, postData, putData } from "../../common/utils/apiUtility";

describe("fetchData", () => {
  beforeEach(() => {
    global.fetch = jest.fn(); // Mock the fetch function
  });

  afterEach(() => {
    jest.clearAllMocks(); // Clear mocks after each test
  });

  it("should fetch data successfully", async () => {
    const mockData = { data: "test" };
    fetch.mockResolvedValueOnce({
      ok: true,
      json: async () => mockData,
    });

    const result = await fetchData("/test-url");
    expect(result).toEqual(mockData);
    expect(fetch).toHaveBeenCalledWith("/test-url");
  });

  it("should throw an error when response is not ok", async () => {
    fetch.mockResolvedValueOnce({
      ok: false,
    });

    await expect(fetchData("/test-url")).rejects.toThrow(
      "Failed to fetch data from /test-url"
    );
  });

  it("should handle fetch error", async () => {
    fetch.mockRejectedValueOnce(new Error("Network error"));

    await expect(fetchData("/test-url", { key: "value" })).rejects.toThrow(
      "Network error"
    );
  });
});

describe("postData", () => {
  beforeEach(() => {
    global.fetch = jest.fn(); // Mock the fetch function
  });

  afterEach(() => {
    jest.clearAllMocks(); // Clear mocks after each test
  });

  it("should post data successfully and return JSON", async () => {
    const mockData = { success: true };
    fetch.mockResolvedValueOnce({
      ok: true,
      headers: {
        get: () => "application/json",
      },
      json: async () => mockData,
    });

    const result = await postData("/test-url", { key: "value" });
    expect(result).toEqual(mockData);
    expect(fetch).toHaveBeenCalledWith(
      "/test-url",
      expect.objectContaining({
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ key: "value" }),
      })
    );
  });

  it("should throw an error when response is not ok", async () => {
    fetch.mockResolvedValueOnce({
      ok: false,
    });

    await expect(postData("/test-url", { key: "value" })).rejects.toThrow(
      "Failed to post data to /test-url"
    );
  });

  it("should handle non-JSON responses gracefully", async () => {
    fetch.mockResolvedValueOnce({
      ok: true,
      headers: {
        get: () => null,
      },
    });

    const result = await postData("/test-url", { key: "value" });
    expect(result).toBeDefined();
    expect(fetch).toHaveBeenCalledWith(
      "/test-url",
      expect.objectContaining({
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ key: "value" }),
      })
    );
  });

  it("should handle fetch error", async () => {
    fetch.mockRejectedValueOnce(new Error("Network error"));

    await expect(postData("/test-url", { key: "value" })).rejects.toThrow(
      "Network error"
    );
  });
});

describe("putData", () => {
  beforeEach(() => {
    global.fetch = jest.fn(); // Mock the fetch function
  });

  afterEach(() => {
    jest.clearAllMocks(); // Clear mocks after each test
  });

  it("should put data successfully and return JSON if available", async () => {
    const mockData = { success: true };
    fetch.mockResolvedValueOnce({
      ok: true,
      headers: {
        get: () => "application/json",
      },
      json: async () => mockData,
    });

    const result = await putData("/test-url", { key: "value" });
    expect(result).toEqual(mockData);
    expect(fetch).toHaveBeenCalledWith(
      "/test-url",
      expect.objectContaining({
        method: "PUT",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ key: "value" }),
      })
    );
  });

  it("should handle no content in response", async () => {
    fetch.mockResolvedValueOnce({
      ok: true,
      headers: {
        get: () => null,
      },
    });

    const result = await putData("/test-url", { key: "value" });
    expect(result).toBeUndefined();
  });

  it("should throw an error when response is not ok", async () => {
    fetch.mockResolvedValueOnce({
      ok: false,
    });

    await expect(putData("/test-url", { key: "value" })).rejects.toThrow(
      "Failed to put data to /test-url"
    );
  });

  it("should handle fetch error", async () => {
    fetch.mockRejectedValueOnce(new Error("Network error"));

    await expect(putData("/test-url", { key: "value" })).rejects.toThrow(
      "Network error"
    );
  });
});
