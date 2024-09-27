import { useQuery } from "@tanstack/react-query";
import { fetchSubject } from "../api/subject";

//async를 사용하는 함수의 캐시,에러 처리를 도와주는 기능
export const useQuerySubject = () => {
  return useQuery({
    queryKey: ["subject"],
    queryFn: fetchSubject,
  });
};
