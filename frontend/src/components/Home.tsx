import { Button, Select } from "@mantine/core";
import styles from "./Home.module.css";
import { useState } from "react";

function Home() {
  const [value, setValue] = useState<string | null>("");

  const showPage = () => {
    if (value !== "") {
      alert("test");
    }
  };

  return (
    <div className={styles.container}>
      <div>당신의 전공을 선택하세요!</div>
      <Select
        className={styles.box}
        placeholder="Pick value"
        data={[
          "컴퓨터공학부",
          "전기전자공학부",
          "의생명공학과",
          "화장품공학과",
        ]}
        value={value}
        onChange={setValue}
      />
      <Button variant="filled" onClick={showPage}>
        입력
      </Button>
    </div>
  );
}

export default Home;
