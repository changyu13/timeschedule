import axios from "axios";

export interface ScheduleId {
  subjectNo: string;
  dow: number;
}
export interface Schedule {
  scheduleId: ScheduleId;
  startTime: number;
  endTime: number;
}

//visual studio에서 requiredList, electiveList dto 보내기
export const postSchedule = async (
  requiredList: string[],
  electiveList: string[]
) => {
  const data = {
    requiredSubjects: requiredList,
    electiveSubjects: electiveList,
  };
  await axios.post("/api/schedule/create", data);
};

export const getSchedule = async () => {
  const res = await axios.get<Schedule[]>("/api/schedule/get");
  return res.data;
};
