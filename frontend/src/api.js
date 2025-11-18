const API_URL = "http://localhost:8080";

// Login user, returns { token: "..." }
export async function login(username, password) {
  const res = await fetch(`${API_URL}/auth/login`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ username, password }),
  });
  if (!res.ok) throw new Error("Login failed");
  return res.json();
}

// Register new user, returns { message: "registered" } or { error: "already exists" }
export async function register(username, password) {
  const res = await fetch(`${API_URL}/auth/register`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ username, password }),
  });
  if (!res.ok) throw new Error("Registration failed");
  return res.json();
}

// Fetch all transactions
export async function getTransactions(token) {
  const res = await fetch(`${API_URL}/api/transactions`, {
    headers: { Authorization: `Bearer ${token}` },
  });
  if (!res.ok) throw new Error("Failed to fetch transactions");
  return res.json();
}

// Create a dispute for a transaction
export async function createDispute(token, transactionId, reason) {
  const res = await fetch(`${API_URL}/api/disputes`, {
    method: "POST",
    headers: { 
      "Content-Type": "application/json",
      Authorization: `Bearer ${token}` 
    },
    body: JSON.stringify({ transactionId, reason }),
  });
  if (!res.ok) throw new Error("Failed to create dispute");
  return res.json();
}

export async function getUserDisputes(token) {
  const res = await fetch(`${API_URL}/api/disputes/user`, {
    headers: { Authorization: `Bearer ${token}` },
  });
  if (!res.ok) throw new Error("Failed to fetch user disputes");
  return res.json();
}
