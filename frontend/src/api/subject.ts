import axios from "axios";
import { Sub } from "../components/Input";

export const fetchSubject = async () => {
  const res = await axios.get<Sub[]>("/api/subject");
  return res.data;
};

export const postSubject = async (subject: Sub[]) => {
  await axios.post("/api/subject", subject);
};
