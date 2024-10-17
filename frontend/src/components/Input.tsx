import {
  Button,
  Modal,
  Paper,
  Select,
  TextInput,
  UnstyledButton,
} from "@mantine/core";
import styles from "./Input.module.css";
import { useDisclosure } from "@mantine/hooks";
import { useState } from "react";
import ShowSubject from "./ShowSubject";
import { FaWindowClose } from "react-icons/fa";
import { useQuerySubject } from "../queries/subject";
import { useMutateSchedule } from "../queries/schedule";
import { Link, useNavigate } from "react-router-dom";

export interface Sub {
  subNo: string;
  courseNo: string;
  subName: string;
  professor: string;
  credit: number;
}
// requiredSubject와 electiveSubject가 초기화가 안 됨. ScheduleTable을 보고 url을 다시 쳐서 들어갔을때도 다시 초기화 해주어야함. 아님 넣었던 과목들을 다시 띄워주던가. 스케줄을 보고 다시 input으로 돌아갔다가 다시 장바구니를 담았을 때

function Input() {
  const [opened, { open, close }] = useDisclosure(false);
  const [searchValue, setSearchValue] = useState("");
  const [maxCredit, setMaxCredit] = useState<number>(0);
  const [requiredSubject, setRequiredSubject] = useState<Sub[]>([]);
  const [electiveSubject, setElectiveSubject] = useState<Sub[]>([]);
  const sub = useQuerySubject();
  const { mutateAsync: sendSubject } = useMutateSchedule();
  const navigate = useNavigate();

  if (sub.isPending) {
    return <div>로딩중...</div>;
  } else if (sub.isError) {
    return <div>오류발생!</div>;
  }

  const subjectNumbers: string[] = [];
  const subject: Sub[] = [];

  const alreadyCal: Set<string> = new Set();
  let creditSum = 0;

  for (const x of requiredSubject) {
    if (alreadyCal.has(x.courseNo)) {
      continue;
    }
    alreadyCal.add(x.courseNo);
    creditSum += x.credit;
  }
  for (const x of electiveSubject) {
    if (alreadyCal.has(x.courseNo)) {
      continue;
    }
    alreadyCal.add(x.courseNo);
  }
  for (let i = 0; i < sub.data.length; i++) {
    subjectNumbers.push(sub.data[i].subjectNo);
    const cmp: Sub = {
      subNo: sub.data[i].subjectNo,
      courseNo: sub.data[i].courseNo,
      subName: sub.data[i].subjectName,
      professor: sub.data[i].professor,
      credit: sub.data[i].credit,
    };
    subject.push(cmp);
  }
  const onClickRequired = (subNumber: string) => {
    //test안에 subject.get(subNo)를 넣어줌으로써 컴파일러가 undefined가 아니라는것을 알게 해줌.
    const test = subject.find((s) => s.subNo === subNumber);
    if (test === undefined) {
      return;
    }
    if (
      requiredSubject.some((x) => x.subNo === test.subNo) ||
      electiveSubject.some((x) => x.subNo === test.subNo)
    ) {
      alert("이미 해당 과목을 추가하셨습니다.");
      return;
    }
    //...은 뒤에오는 배열 or 딕셔너리의 원소를 풀어 헤치는 문법
    if (creditSum + test.credit > maxCredit) {
      alert("최대학점을 초과했습니다.");
      return;
    }
    setRequiredSubject((prev) => [...prev, test]);
  };
  const onClickElective = (subNumber: string) => {
    //test안에 subject.get(subNo)를 넣어줌으로써 컴파일러가 undefined가 아니라는것을 알게 해줌.
    const test = subject.find((s) => s.subNo === subNumber);
    if (test === undefined) {
      return;
    }
    if (
      requiredSubject.some((x) => x.subNo === test.subNo) ||
      electiveSubject.some((x) => x.subNo === test.subNo)
    ) {
      alert("이미 해당 과목을 추가하셨습니다.");
      return;
    }
    //...은 뒤에오는 배열 or 딕셔너리의 원소를 풀어 헤치는 문법
    if (creditSum + test.credit > maxCredit) {
      alert("최대학점을 초과했습니다.");
      return;
    }
    setElectiveSubject((prev) => [...prev, test]);
  };
  //에러처리를 위해 임시 사용
  const isExist = (list: Sub[], subNumber: string) => {
    const test = list.find((x: Sub) => x.subNo === subNumber);
    if (test === undefined) {
      return list[0];
    }
    return test;
  };

  const requiredSubjectNodes = requiredSubject.map((x) => (
    <div key={x.subNo} className={styles.entity}>
      <div className={styles.entityContents}>
        <div>과목 : {x.subName}</div>
        <div>교수 : {x.professor}</div>
        <div>학점 : {x.credit}</div>
      </div>
      <UnstyledButton
        onClick={() => {
          setRequiredSubject((prev) =>
            prev.filter((now) => x.subNo !== now.subNo)
          );
          console.log(requiredSubject);
        }}
      >
        <FaWindowClose />
      </UnstyledButton>
    </div>
  ));
  const electiveSubjectNodes = electiveSubject.map((x) => (
    <div key={x.subNo} className={styles.entity}>
      <div className={styles.entityContents}>
        <div>과목 : {x.subName}</div>
        <div>교수 : {x.professor}</div>
        <div>학점 : {x.credit}</div>
      </div>
      <UnstyledButton
        onClick={() => {
          setElectiveSubject((prev) =>
            prev.filter((now) => x.subNo !== now.subNo)
          );
        }}
      >
        <FaWindowClose />
      </UnstyledButton>
    </div>
  ));
  return (
    <div className={styles.container}>
      <div className={styles.showCredit}> 현재 학점 : {creditSum}</div>
      <Paper shadow="xs" radius="xs" p="xs" className={styles.basket}>
        <div>필수 과목</div>
        <div className={styles.entityBasket}>{requiredSubjectNodes}</div>
      </Paper>
      <Paper shadow="xs" radius="xs" p="xs" className={styles.basket}>
        <div>선택 과목</div>
        <div className={styles.entityBasket}>{electiveSubjectNodes}</div>
      </Paper>
      <Paper shadow="xs" radius="xs" p="xs" className={styles.userInput}>
        <div>학점을 입력하세요 (최대 21점)</div>
        <div className={styles.userContainer}>
          <TextInput
            className={styles.credits}
            placeholder="숫자만 입력"
            value={maxCredit}
            onChange={(event) =>
              setMaxCredit(Number(event.currentTarget.value))
            }
          />
          <div>
            <Button className={styles.btn} onClick={open}>
              과목 추가
            </Button>
            <Button
              className={styles.btn}
              onClick={() => {
                const requiredSubjectString = requiredSubject.map(
                  (x) => x.subNo
                );
                const electiveSubjectString = electiveSubject.map(
                  (x) => x.subNo
                );
                sendSubject({
                  requiredList: requiredSubjectString,
                  electiveList: electiveSubjectString,
                  userCredit: maxCredit,
                });
                navigate("/schedule");
              }}
            >
              시간표 생성
            </Button>
          </div>
        </div>
      </Paper>
      <Modal opened={opened} onClose={close} title="강의 추가" centered>
        <Select
          searchable
          label="과목번호를 입력하세요"
          placeholder="숫자만 입력"
          data={subjectNumbers}
          searchValue={searchValue}
          onSearchChange={setSearchValue}
        />
        <ShowSubject
          subNo={searchValue}
          subName={isExist(subject, searchValue).subName}
          professor={isExist(subject, searchValue).professor}
          credit={isExist(subject, searchValue).credit}
          onClickRequired={onClickRequired}
          onClickElective={onClickElective}
        />
      </Modal>
    </div>
  );
}

export default Input;
