import { useState } from "react";
import Login from "./components/Login";
import Transactions from "./components/Transactions";
import Disputes from "./components/Disputes";
import { Tab, Tabs, TabList, TabPanel } from "react-tabs";
import "react-tabs/style/react-tabs.css";

function App() {
  const [token, setToken] = useState(null);
  const [disputeTx, setDisputeTx] = useState(null);
  const [activeTab, setActiveTab] = useState(0);

  if (!token) return <Login setToken={setToken} />;

  const handleSuccess = () => {
    setDisputeTx(null);
    setActiveTab(0); // Go back to Transactions tab
  };

  return (
    <div style={styles.container}>
      <header style={styles.header}>
        <h1>Transactions Dispute Portal</h1>
      </header>

      {!disputeTx ? (
        <Tabs selectedIndex={activeTab} onSelect={setActiveTab}>
          <TabList style={styles.tabList}>
            <Tab>Transactions</Tab>
            <Tab>Dispute History</Tab>
          </TabList>

          <TabPanel>
            <Transactions token={token} onDispute={setDisputeTx} />
          </TabPanel>
          <TabPanel>
            <Transactions token={token} showOnlyHistory={true} />
          </TabPanel>
        </Tabs>
      ) : (
        <div>
          <button style={styles.backBtn} onClick={handleSuccess}>
            Back
          </button>
          <Disputes token={token} transactionId={disputeTx} onSuccess={handleSuccess} />
        </div>
      )}
    </div>
  );
}

const styles = {
  container: { maxWidth: "1000px", margin: "0 auto", padding: "20px", fontFamily: "Segoe UI, sans-serif" },
  header: { textAlign: "center", marginBottom: "20px", color: "#1a73e8" },
  tabList: { display: "flex", borderBottom: "2px solid #ddd", marginBottom: "20px" },
  backBtn: { marginBottom: "16px", padding: "8px 16px", background: "#eee", border: "none", borderRadius: "4px" },
};

export default App;