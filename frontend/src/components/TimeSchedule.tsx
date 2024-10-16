import styles from "./TimeSchedule.module.css";
import { useQuerySchedule } from "../queries/schedule";
import { Schedule } from "../api/schedule";
import { periodToTime } from "../util/periodToTime";
import { useState } from "react";
import ScheduleTable from "./ScheduleTable";
import { useQuerySubject } from "../queries/subject";

function TimeSchedule() {
  const timeData = useQuerySchedule();
  const [scheduleIdx, setScheduleIdx] = useState(0);
  if (timeData.isPending) {
    return <div>로딩중...</div>;
  } else if (timeData.isError) {
    return <div>오류발생!</div>;
  }

  const menu = [
    timeData.data.manyEmpty,
    timeData.data.littleWait,
    timeData.data.noMorning,
  ];
  const courseList = menu[scheduleIdx];

  return (
    <div className={styles.container}>
      <div className={styles.btnContainer}>
        <button
          onClick={() => {
            setScheduleIdx(0);
          }}
        >
          최대 공강
        </button>
        <button
          onClick={() => {
            setScheduleIdx(1);
          }}
        >
          짧은 수업 텀
        </button>
        <button
          onClick={() => {
            setScheduleIdx(2);
          }}
        >
          잠꾸러기
        </button>
      </div>
      <ScheduleTable courseList={courseList} />
    </div>
  );
}

export default TimeSchedule;
