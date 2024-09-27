import axios from "axios";

interface Subject {
  subjectNo: string;
  courseNo: string;
  subjectName: string;
  professor: string;
  credit: number;
}

export const fetchSubject = async () => {
  const res = await axios.get<Subject[]>("/api/subject");
  return res.data;
};
