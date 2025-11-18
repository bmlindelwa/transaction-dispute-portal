export default function DisputeHistory({ disputes }) {
  if (!disputes.length) return <p style={{ color: "#666" }}>No disputes yet.</p>;

  return (
    <div style={styles.grid}>
      {disputes.map(d => (
        <div key={d.id} style={styles.card}>
          <div style={styles.row}>
            <strong>Tx ID:</strong> {d.transaction.id} | R{d.transaction.amount}
          </div>
          <div style={styles.row}><strong>Reason:</strong> {d.reason}</div>
          <div style={styles.row}>
            <strong>Status:</strong>{" "}
            <span style={statusStyle(d.status)}>{d.status}</span>
          </div>
          <div style={styles.row}>
            <strong>Date:</strong> {new Date(d.createdAt).toLocaleDateString()}
          </div>
        </div>
      ))}
    </div>
  );
}

const statusStyle = (s) => ({
  padding: "4px 8px",
  borderRadius: "12px",
  fontSize: "12px",
  fontWeight: "bold",
  color: "white",
  backgroundColor:
    s === "APPROVED" ? "#28a745" :
    s === "REJECTED" ? "#dc3545" :
    s === "OPEN" ? "#ffc107" : "#6c757d",
});

const styles = {
  grid: { display: "grid", gap: "16px", gridTemplateColumns: "repeat(auto-fill, minmax(280px, 1fr))" },
  card: {
    background: "white",
    border: "1px solid #e0e0e0",
    borderRadius: "12px",
    padding: "16px",
    boxShadow: "0 2px 8px rgba(0,0,0,0.05)",
    transition: "transform 0.2s",
  },
  row: { margin: "6px 0", fontSize: "14px", color: "#333" },
};