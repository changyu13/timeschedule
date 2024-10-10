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
export const fetchSchedule = async () => {
  const res = await axios.post<Schedule[]>("/api/schedule/add");
  return res.data;
};
