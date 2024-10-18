import { useMutation, useQuery } from "@tanstack/react-query";
import { fetchSubject, postSubject } from "../api/subject";
import { Sub } from "../components/Input";

interface Params {
  subject: Sub[];
}
//async를 사용하는 함수의 캐시,에러 처리를 도와주는 기능
export const useQuerySubject = () => {
  return useQuery({
    queryKey: ["subject"],
    queryFn: fetchSubject,
  });
};

export const useMutateSubject = () => {
  return useMutation({
    mutationFn: (params: Params) => postSubject(params.subject),
  });
};
