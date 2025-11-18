import { useEffect, useState } from "react";
import { getTransactions, getUserDisputes } from "../api";
import DisputeHistory from "./DisputeHistory";

export default function Transactions({ token, onDispute, showOnlyHistory }) {
  const [transactions, setTransactions] = useState([]);
  const [disputes, setDisputes] = useState([]);

  useEffect(() => {
    const fetchData = async () => {
      const [txs, allDisputes] = await Promise.all([
        getTransactions(token),
        getUserDisputes(token),
      ]);
      setTransactions(txs);
      setDisputes(allDisputes);
    };
    fetchData();
  }, [token]);

  const disputedTxIds = disputes.map(d => d.transaction.id);

  if (showOnlyHistory) {
    return <DisputeHistory disputes={disputes} />;
  }

  return (
    <div style={styles.grid}>
      {transactions.map(tx => (
        <div key={tx.id} style={styles.card}>
          <div style={styles.id}>ID: {tx.id}</div>
          <div style={styles.amount}>R{tx.amount}</div>
          {disputedTxIds.includes(tx.id) ? (
            <span style={styles.disputed}>Already disputed</span>
          ) : (
            <button style={styles.btn} onClick={() => onDispute(tx.id)}>
              Dispute
            </button>
          )}
        </div>
      ))}
    </div>
  );
}

const styles = {
  grid: {
    display: "grid",
    gap: "16px",
    gridTemplateColumns: "repeat(auto-fill, minmax(280px, 1fr))",
  },
  card: {
    background: "white",
    border: "1px solid #e0e0e0",
    borderRadius: "12px",
    padding: "16px",
    boxShadow: "0 2px 8px rgba(0,0,0,0.05)",
  },
  id: { fontWeight: "bold", fontSize: "16px" },
  amount: { color: "#1a73e8", fontSize: "14px", margin: "4px 0" },
  btn: {
    marginTop: "8px",
    padding: "8px 16px",
    backgroundColor: "#dc3545",
    color: "white",
    border: "none",
    borderRadius: "6px",
    cursor: "pointer",
    fontSize: "14px",
  },
  disputed: {
    color: "#dc3545",
    fontWeight: "bold",
    fontSize: "14px",
  },
};
