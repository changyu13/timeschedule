import styles from "./TimeSchedule.module.css";
import { useQuerySchedule } from "../queries/schedule";
import { Schedule } from "../api/schedule";
import { periodToTime } from "../util/periodToTime";

function TimeSchedule() {
  const courseList: Schedule[] = [];
  const courseListNodes: JSX.Element[] = [];
  const timeData = useQuerySchedule();

  if (timeData.isPending) {
    return <div>로딩중...</div>;
  } else if (timeData.isError) {
    return <div>오류발생!</div>;
  }
  for (let i = 0; i < timeData.data.length; i++) {
    courseList.push(timeData.data[i]);
  }
  for (let period = 0; period <= 23; period++) {
    const dowList = [];
    for (let j = 1; j <= 6; j++) {
      let hasCourse = false;
      for (const x of courseList) {
        if (
          x.scheduleId.dow === j &&
          x.startTime <= period &&
          period < x.endTime
        ) {
          console.log(x.startTime + "/" + period + "/" + x.endTime);
          dowList.push(
            <td
              key={j}
              style={{
                backgroundColor: `rgb(${
                  (Number(x.scheduleId.subjectNo) % 256) +
                  30 * Number(x.scheduleId.subjectNo)
                }
                ,${
                  (Number(x.scheduleId.subjectNo) % 256) +
                  30 * Number(x.scheduleId.subjectNo)
                }
                ,${
                  (Number(x.scheduleId.subjectNo) % 256) +
                  30 * Number(x.scheduleId.subjectNo)
                })`,
              }}
            ></td>
          );
          hasCourse = true;
          break;
        }
      }
      if (hasCourse === false) {
        dowList.push(<td key={j}></td>);
      }
    }
    console.log(dowList.length);
    courseListNodes.push(
      <tr key={period}>
        <td>{periodToTime(period)}</td>
        {dowList}
      </tr>
    );
  }
  return (
    <div className={styles.container}>
      <div className={styles.btnContainer}>
        <button>최대 공강</button>
        <button>짧은 수업 텀</button>
        <button>잠꾸러기</button>
      </div>
      <table className={styles.board}>
        <tr>
          <td></td>
          <td>월</td>
          <td>화</td>
          <td>수</td>
          <td>목</td>
          <td>금</td>
          <td>토</td>
        </tr>
        {courseListNodes}
      </table>
    </div>
  );
}

export default TimeSchedule;
