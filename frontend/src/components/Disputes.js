import { useState } from "react";
import { createDispute } from "../api";

export default function Disputes({ token, transactionId, onSuccess }) {
  const [reason, setReason] = useState("");

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      await createDispute(token, transactionId, reason);
      alert("Dispute submitted successfully");
      onSuccess();
    } catch (err) {
      alert(err.message);
    }
  };

  return (
    <div style={formStyles.container}>
      <h3>Dispute Transaction {transactionId}</h3>
      <form onSubmit={handleSubmit} style={formStyles.form}>
        <textarea
          placeholder="Reason for dispute..."
          value={reason}
          onChange={(e) => setReason(e.target.value)}
          required
          style={formStyles.textarea}
        />
        <div style={formStyles.actions}>
          <button type="button" onClick={onSuccess} style={formStyles.cancel}>
            Cancel
          </button>
          <button type="submit" style={formStyles.submit}>
            Submit Dispute
          </button>
        </div>
      </form>
    </div>
  );
}

const formStyles = {
  container: { maxWidth: "500px", margin: "0 auto" },
  form: { display: "flex", flexDirection: "column", gap: "12px" },
  textarea: { padding: "12px", borderRadius: "8px", border: "1px solid #ccc", fontSize: "14px" },
  actions: { display: "flex", gap: "12px", justifyContent: "flex-end" },
  cancel: { padding: "10px 16px", background: "#f8f9fa", border: "1px solid #ddd", borderRadius: "6px" },
  submit: { padding: "10px 16px", background: "#dc3545", color: "white", border: "none", borderRadius: "6px" },
};