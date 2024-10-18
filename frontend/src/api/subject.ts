import axios from "axios";

export interface Sub {
  subjectNo: string;
  courseNo: string;
  subName: string;
  professor: string;
  credit: number;
}

export const fetchSubject = async () => {
  const res = await axios.get<Sub[]>("/api/subject");
  return res.data;
};

export const postSubject = async (subject: Sub[]) => {
  await axios.post("/api/subject", subject);
};
