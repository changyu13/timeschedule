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

interface RecommendedSchdule {
  manyEmpty: Schedule[];
  littleWait: Schedule[];
  noMorning: Schedule[];
}

//visual studio에서 requiredList, electiveList dto 보내기
export const postSchedule = async (
  requiredList: string[],
  electiveList: string[],
  userCredit: number
) => {
  const data = {
    requiredSubjects: requiredList,
    electiveSubjects: electiveList,
    userCredit: userCredit,
  };
  await axios.post("/api/schedule/create", data);
};

export const getSchedule = async () => {
  const res = await axios.get<RecommendedSchdule>("/api/schedule/get");
  return res.data;
};
