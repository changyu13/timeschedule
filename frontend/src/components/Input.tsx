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
import {
  useMutateAddSubject,
  useMutateDeleteSubject,
  useQuerySubject,
  useQuerySubjectToAdd,
} from "../queries/subject";
import { useMutateSchedule } from "../queries/schedule";
import { useNavigate } from "react-router-dom";
import { Sub } from "../api/subject";

// requiredSubject와 electiveSubject가 초기화가 안 됨. ScheduleTable을 보고 url을 다시 쳐서 들어갔을때도 다시 초기화 해주어야함. 아님 넣었던 과목들을 다시 띄워주던가. 스케줄을 보고 다시 input으로 돌아갔다가 다시 장바구니를 담았을 때
export interface SubToAdd {
  subjectNo: string;
  required: boolean;
}
//추가할 때 requioe
const Input = () => {
  const [opened, { open, close }] = useDisclosure(false);
  const [searchValue, setSearchValue] = useState("");
  const [maxCredit, setMaxCredit] = useState<number>(0);

  const subjectToAddList = useQuerySubjectToAdd();

  const requiredSubjectNodes: JSX.Element[] = [];
  const electiveSubjectNodes: JSX.Element[] = [];

  const sub = useQuerySubject();

  const { mutateAsync: sendSchedule } = useMutateSchedule();
  const { mutateAsync: sendSubject } = useMutateAddSubject();
  const { mutateAsync: deleteSubject } = useMutateDeleteSubject();

  const navigate = useNavigate();

  if (sub.isPending) {
    return <div>로딩중...</div>;
  } else if (sub.isError) {
    return <div>오류발생!</div>;
  }
  if (subjectToAddList.isPending) {
    return <div>추가할 과목 로딩중...</div>;
  } else if (subjectToAddList.isError) {
    return <div>추가할 과목 오류발생!</div>;
  }

  const subjectNumbers: string[] = [];

  const alreadyCal: Set<string> = new Set();
  let creditSum = 0;

  // 과목 고르는 SELECT 메뉴에 들어갈 subjectNo
  for (let i = 0; i < sub.data.length; i++) {
    subjectNumbers.push(sub.data[i].subjectNo);
  }

  for (const x of subjectToAddList.data) {
    const target = sub.data.find((now) => now.subjectNo === x.subjectNo);
    console.log(target);
    if (target === undefined) {
      continue;
    }
    if (x.required == true) {
      if (!alreadyCal.has(target.courseNo)) {
        alreadyCal.add(target.courseNo);
        creditSum += target.credit;
      }
    }
    if (x.required === true) {
      requiredSubjectNodes.push(
        <div key={x.subjectNo} className={styles.entity}>
          <div className={styles.entityContents}>
            <div>과목 : {target.subjectName}</div>
            <div>교수 : {target.professor}</div>
            <div>학점 : {target.credit}</div>
          </div>
          <UnstyledButton
            onClick={() => {
              const subToDelete: SubToAdd = {
                subjectNo: target.subjectNo,
                required: true,
              };
              deleteSubject({ subject: subToDelete });
            }}
          >
            <FaWindowClose />
          </UnstyledButton>
        </div>
      );
    } else {
      electiveSubjectNodes.push(
        <div key={x.subjectNo} className={styles.entity}>
          <div className={styles.entityContents}>
            <div>과목 : {target.subjectName}</div>
            <div>교수 : {target.professor}</div>
            <div>학점 : {target.credit}</div>
          </div>
          <UnstyledButton
            onClick={() => {
              const subToDelete: SubToAdd = {
                subjectNo: target.subjectNo,
                required: false,
              };
              deleteSubject({ subject: subToDelete });
            }}
          >
            <FaWindowClose />
          </UnstyledButton>
        </div>
      );
    }
  }

  const onClickRequired = (subjectNo: string) => {
    //test안에 subject.get(subNo)를 넣어줌으로써 컴파일러가 undefined가 아니라는것을 알게 해줌.
    const test = sub.data.find((s) => s.subjectNo === subjectNo);
    if (test === undefined) {
      return;
    }
    if (subjectToAddList.data.some((x) => x.subjectNo === test.subjectNo)) {
      alert("이미 해당 과목을 추가하셨습니다.");
      return;
    }
    if (
      subjectToAddList.data.some((x) => {
        const target = sub.data.find((now) => now.subjectNo === x.subjectNo);
        const subjectToAdd = sub.data.find(
          (now) => now.subjectNo === subjectNo
        );
        if (subjectToAdd?.courseNo == target?.courseNo) {
          return true;
        }
      })
    ) {
      alert("이미 같은 교과번호의 과목이 추가 됨");
      return;
    }
    if (creditSum + test.credit > maxCredit) {
      //...은 뒤에오는 배열 or 딕셔너리의 원소를 풀어 헤치는 문법
      alert("최대학점을 초과했습니다.");
      return;
    }
    const subToAdd: SubToAdd = {
      subjectNo: test.subjectNo,
      required: true,
    };
    sendSubject({ subject: subToAdd });
  };

  const onClickElective = (subjectNo: string) => {
    //test안에 subject.get(subNo)를 넣어줌으로써 컴파일러가 undefined가 아니라는것을 알게 해줌.
    const test = sub.data.find((s) => s.subjectNo === subjectNo);
    if (test === undefined) {
      return;
    }
    if (subjectToAddList.data.some((x) => x.subjectNo === test.subjectNo)) {
      alert("이미 해당 과목을 추가하셨습니다.");
      return;
    }
    if (
      subjectToAddList.data.some((x) => {
        if (x.required == true) {
          const target = sub.data.find((now) => now.subjectNo === x.subjectNo);
          const subjectToAdd = sub.data.find(
            (now) => now.subjectNo === subjectNo
          );
          if (subjectToAdd?.courseNo == target?.courseNo) {
            return true;
          }
        }
      })
    ) {
      alert("이미 필수 과목에 같은 교과번호의 과목이 추가 됨");
      return;
    }
    //...은 뒤에오는 배열 or 딕셔너리의 원소를 풀어 헤치는 문법
    if (creditSum + test.credit > maxCredit) {
      alert("최대학점을 초과했습니다.");
      return;
    }
    const subToAdd: SubToAdd = {
      subjectNo: test.subjectNo,
      required: false,
    };
    sendSubject({ subject: subToAdd });
  };
  //에러처리를 위해 임시 사용(변경할 것)
  /*const isExist = (list: Sub[], subNumber: string) => {
    const test = list.find((x: Sub) => x.subjectNo === subNumber);
    if (test === undefined) {
      return list[0];
    }
    return test;
  };*/
  const selectedSubject = sub.data.find(
    (x: Sub) => x.subjectNo === searchValue
  );

  //subjectToAddList에서 required boolean type을 이용한 분류를 어디서 시작할지 정해야 함.
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
                sendSchedule({
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
        {selectedSubject !== undefined ? (
          <ShowSubject
            subNo={searchValue}
            subName={selectedSubject.subjectName}
            professor={selectedSubject.professor}
            credit={selectedSubject.credit}
            onClickRequired={onClickRequired}
            onClickElective={onClickElective}
          />
        ) : (
          <div>추가할 과목을 선택해주세요!</div>
        )}
      </Modal>
    </div>
  );
};

export default Input;
