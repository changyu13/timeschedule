import axios from "axios";
import { SubToAdd } from "../components/Input";

export interface Sub {
  subjectNo: string;
  courseNo: string;
  subjectName: string;
  professor: string;
  credit: number;
}

export const fetchSubject = async () => {
  const res = await axios.get<Sub[]>("/api/subject");
  return res.data;
};

export const fetchSubjectToAdd = async () => {
  const res = await axios.get<SubToAdd[]>("/api/subjectToAdd");
  return res.data;
};

export const addSubject = async (subject: SubToAdd) => {
  await axios.post("/api/subject", subject);
};
export const deleteSubject = async (subject: SubToAdd) => {
  await axios.delete("/api/subject", {
    data: subject,
  });
};
