import { Schedule } from "../api/schedule";
import { useQuerySubject } from "../queries/subject";
import { periodToTime } from "../util/periodToTime";
import styles from "./ScheduleTable.module.css";

interface Props {
  courseList: Schedule[];
}

const COLORS: string[] = [
  "#EF9A9A",
  "#CE93D8",
  "#9FA8DA",
  "#90CAF9",
  "#80DEEA",
  "#80CBC4",
  "#E6EE9C",
  "#FFF59D",
  "#FFA726",
  "#FFAB91",
  "#BCAAA4",
  "#9E9E9E",
  "#607D8B",
];

const ScheduleTable: React.FC<Props> = ({ courseList }) => {
  const sub = useQuerySubject();
  if (sub.isPending) {
    return <div>로딩중...</div>;
  } else if (sub.isError) {
    return <div>오류발생!</div>;
  }
  if (courseList == null) {
    return <div>해당 시간표 없음!</div>;
  }
  const courseListNodes: JSX.Element[] = [];
  const colorMap = new Map<string, string>();
  const eLearning = new Set<Schedule>();
  const eLearningNodes: JSX.Element[] = [];
  let colorIdx = 0;
  for (let period = 0; period <= 23; period++) {
    const dowList = [];
    for (let j = 1; j <= 6; j++) {
      let hasCourse = false;
      for (const x of courseList) {
        if (x.startTime === 30 && x.endTime === 30) {
          eLearning.add(x);
          if (!colorMap.has(x.scheduleId.subjectNo)) {
            colorMap.set(x.scheduleId.subjectNo, COLORS[colorIdx]);
            colorIdx++;
          }
        } else if (
          x.scheduleId.dow === j &&
          x.startTime <= period &&
          period < x.endTime
        ) {
          console.log(x.startTime + "/" + period + "/" + x.endTime);
          if (!colorMap.has(x.scheduleId.subjectNo)) {
            colorMap.set(x.scheduleId.subjectNo, COLORS[colorIdx]);
            colorIdx++;
          }
          const currentSubject = sub.data.find(
            (now) => now.subjectNo === x.scheduleId.subjectNo
          );
          if (currentSubject === undefined) {
            return <div>에러</div>;
          }
          if (x.endTime - x.startTime >= 2) {
            if (x.scheduleId.subjectNo == "0059") {
              console.log("colorMap:" + colorMap.get(x.scheduleId.subjectNo));
            }
            if (x.startTime === period) {
              dowList.push(
                <td
                  key={j}
                  style={{
                    backgroundColor: colorMap.get(x.scheduleId.subjectNo),
                  }}
                  rowSpan={x.endTime - x.startTime}
                >
                  {currentSubject.subjectName}
                </td>
              );
            }
          } else {
            dowList.push(
              <td
                key={j}
                style={{
                  backgroundColor: colorMap.get(x.scheduleId.subjectNo),
                }}
              >
                {currentSubject.subjectName}
              </td>
            );
          }
          hasCourse = true;
          break;
        }
      }
      if (hasCourse === false) {
        dowList.push(<td key={j}></td>);
      }
    }
    courseListNodes.push(
      <tr key={period}>
        <td>{periodToTime(period)}</td>
        {dowList}
      </tr>
    );
  }
  for (const x of eLearning) {
    const currentSubject = sub.data.find(
      (now) => now.subjectNo === x.scheduleId.subjectNo
    );
    if (currentSubject === undefined) {
      return <div>에러</div>;
    }
    eLearningNodes.push(
      <div
        key={currentSubject.subjectNo}
        className={styles.eLearning}
        style={{
          backgroundColor: colorMap.get(currentSubject.subjectNo),
        }}
      >
        {currentSubject.subjectName}
      </div>
    );
  }
  return (
    <div className={styles.back}>
      <table className={styles.board}>
        <thead>
          <tr>
            <td></td>
            <td>월</td>
            <td>화</td>
            <td>수</td>
            <td>목</td>
            <td>금</td>
            <td>토</td>
          </tr>
        </thead>
        <tbody>{courseListNodes}</tbody>
      </table>
      {eLearningNodes}
    </div>
  );
};

export default ScheduleTable;
