import { Button, Modal, Paper, Select, TextInput } from "@mantine/core";
import styles from "./Input.module.css";
import { useDisclosure } from "@mantine/hooks";
import { ReactNode, useState } from "react";
import ShowSubject from "./ShowSubject";
function Input() {
  const [opened, { open, close }] = useDisclosure(false);
  const [searchValue, setSearchValue] = useState("");
  const rea: {
    subNo: string;
    subName: string;
    professor: string;
    credit: number;
  }[] = [];
  const [requiredSubject, setRequiredSubject] = useState(rea);
  const subject = new Map([
    [
      "1111",
      {
        subNo: "1111",
        subName: "알고리즘",
        professor: "조현득",
        credit: 3,
      },
    ],
    [
      "2222",
      {
        subNo: "2222",
        subName: "화학",
        professor: "이정현",
        credit: 2,
      },
    ],
    [
      "3333",
      {
        subNo: "3333",
        subName: "생명",
        professor: "김승오",
        credit: 2,
      },
    ],
    [
      "4444",
      {
        subNo: "4444",
        subName: "경제",
        professor: "김준재",
        credit: 3,
      },
    ],
  ]);
  const onClickRequired = (subNo: string) => {
    //test안에 subject.get(subNo)를 넣어줌으로써 컴파일러가 undefined가 아니라는것을 알게 해줌.
    const test = subject.get(subNo);
    if (test !== undefined) {
      //...은 뒤에오는 배열 or 딕셔너리의 원소를 풀어 헤치는 문법
      setRequiredSubject((prev) => [...prev, test]);
    }
  };

  const requiredSubjectNodes = requiredSubject.map((x) => (
    <div key={x.subNo}>과목: {x.subName}</div>
  ));
  return (
    <div className={styles.container}>
      <Paper shadow="xs" radius="xs" p="xs" className={styles.basket}>
        필수 과목
        {requiredSubjectNodes}
      </Paper>
      <Paper shadow="xs" radius="xs" p="xs" className={styles.basket}>
        선택 과목
      </Paper>
      <Paper shadow="xs" radius="xs" p="xs" className={styles.userInput}>
        <div>학점을 입력하세요 (최대 21점)</div>
        <div>
          <TextInput className={styles.credits} placeholder="숫자만 입력" />
          <Button onClick={open}>Open modal</Button>
        </div>
      </Paper>
      <Modal opened={opened} onClose={close} title="강의 추가" centered>
        <Select
          searchable
          label="과목번호를 입력하세요"
          placeholder="숫자만 입력"
          data={["1111", "2222", "3333", "4444"]}
          searchValue={searchValue}
          onSearchChange={setSearchValue}
        />
        <ShowSubject
          subNo={searchValue}
          subName={subject.get(searchValue)?.subName}
          professor={subject.get(searchValue)?.professor}
          credit={subject.get(searchValue)?.credit}
          onClickRequired={onClickRequired}
        />
      </Modal>
    </div>
  );
}

export default Input;
