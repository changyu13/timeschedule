import "@mantine/core/styles.css";
import { MantineProvider } from "@mantine/core";
import styles from "./App.module.css";
import Home from "./components/Home";
import { BrowserRouter, Route, Routes } from "react-router-dom";
import Input from "./components/Input";

function App() {
  return (
    <div className={styles.back}>
      <MantineProvider>
        <BrowserRouter>
          <Routes>
            <Route path="/" element={<Home />}></Route>
            <Route path="/input" element={<Input />}></Route>
          </Routes>
        </BrowserRouter>
      </MantineProvider>
    </div>
  );
}

export default App;
