import {
  login,
  register,
  getTransactions,
  createDispute,
  getUserDisputes
} from "../api";  

// Mock the global fetch function
global.fetch = jest.fn();

beforeEach(() => {
  fetch.mockClear();
});

describe("API Tests", () => {
  // ----------------------------------
  // LOGIN TESTS
  // ----------------------------------
  test("login() returns token on success", async () => {
    const mockToken = { token: "12345" };

    fetch.mockResolvedValueOnce({
      ok: true,
      json: async () => mockToken
    });

    const result = await login("john", "pass123");
    expect(result).toEqual(mockToken);
    expect(fetch).toHaveBeenCalledWith(
      "http://localhost:8080/auth/login",
      expect.objectContaining({
        method: "POST",
      })
    );
  });

  test("login() throws error on failure", async () => {
    fetch.mockResolvedValueOnce({ ok: false });

    await expect(login("wrong", "bad")).rejects.toThrow("Login failed");
  });

  // ----------------------------------
  // REGISTER TESTS
  // ----------------------------------
  test("register() returns message on success", async () => {
    const mockResponse = { message: "registered" };

    fetch.mockResolvedValueOnce({
      ok: true,
      json: async () => mockResponse
    });

    const result = await register("newuser", "pass123");
    expect(result).toEqual(mockResponse);
  });

  test("register() throws error when user exists", async () => {
    fetch.mockResolvedValueOnce({ ok: false });

    await expect(register("duplicate", "pass"))
      .rejects
      .toThrow("Registration failed");
  });

  // ----------------------------------
  // GET TRANSACTIONS TESTS
  // ----------------------------------
  test("getTransactions() returns list", async () => {
    const mockTransactions = [{ id: 1, amount: 100 }];

    fetch.mockResolvedValueOnce({
      ok: true,
      json: async () => mockTransactions
    });

    const result = await getTransactions("token123");
    expect(result).toEqual(mockTransactions);
    expect(fetch).toHaveBeenCalledWith(
      "http://localhost:8080/api/transactions",
      expect.objectContaining({
        headers: { Authorization: "Bearer token123" }
      })
    );
  });

  // ----------------------------------
  // CREATE DISPUTE TESTS
  // ----------------------------------
  test("createDispute() sends dispute and returns response", async () => {
    const mockResponse = { disputeId: 10 };

    fetch.mockResolvedValueOnce({
      ok: true,
      json: async () => mockResponse
    });

    const result = await createDispute("tkn", 1, "Wrong amount");

    expect(result).toEqual(mockResponse);
    expect(fetch).toHaveBeenCalledWith(
      "http://localhost:8080/api/disputes",
      expect.objectContaining({
        method: "POST",
      })
    );
  });

  test("createDispute() throws on failure", async () => {
    fetch.mockResolvedValueOnce({ ok: false });

    await expect(createDispute("bad", 1, "Wrong")).rejects.toThrow(
      "Failed to create dispute"
    );
  });

  // ----------------------------------
  // GET USER DISPUTES TESTS
  // ----------------------------------
  test("getUserDisputes() returns user disputes", async () => {
    const mockData = [{ id: 1, reason: "fraud" }];

    fetch.mockResolvedValueOnce({
      ok: true,
      json: async () => mockData,
    });

    const result = await getUserDisputes("tkn123");
    expect(result).toEqual(mockData);
  });

  test("getUserDisputes() throws on failure", async () => {
    fetch.mockResolvedValueOnce({ ok: false });

    await expect(getUserDisputes("failToken"))
      .rejects
      .toThrow("Failed to fetch user disputes");
  });
});
