import "@mantine/core/styles.css";
import { MantineProvider } from "@mantine/core";
import styles from "./App.module.css";
import { BrowserRouter, Route, Routes } from "react-router-dom";
import Input from "./components/Input";
import { QueryClientProvider } from "@tanstack/react-query";
import { queryClient } from "./globals";
import TimeSchedule from "./components/TimeSchedule";

function App() {
  return (
    <div className={styles.back}>
      <QueryClientProvider client={queryClient}>
        <MantineProvider>
          <BrowserRouter>
            <Routes>
              <Route path="/" element={<Input />}></Route>
              <Route path="/schedule" element={<TimeSchedule />}></Route>
            </Routes>
          </BrowserRouter>
        </MantineProvider>
      </QueryClientProvider>
    </div>
  );
}

export default App;
