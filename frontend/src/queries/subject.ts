import { useMutation, useQuery } from "@tanstack/react-query";
import {
  fetchSubject,
  fetchSubjectToAdd,
  addSubject,
  deleteSubject,
} from "../api/subject";
import { SubToAdd } from "../components/Input";
import { queryClient } from "../globals";

interface Params {
  subject: SubToAdd;
}
//async를 사용하는 함수의 캐시,에러 처리를 도와주는 기능
export const useQuerySubject = () => {
  return useQuery({
    queryKey: ["subject"],
    queryFn: fetchSubject,
  });
};

export const useQuerySubjectToAdd = () => {
  return useQuery({
    queryKey: ["subjectToAdd"],
    queryFn: fetchSubjectToAdd,
  });
};

export const useMutateAddSubject = () => {
  return useMutation({
    mutationFn: (params: Params) => addSubject(params.subject),
    onMutate: async (params: Params) => {
      await queryClient.cancelQueries({ queryKey: ["subjectToAdd"] });
      const preSubjectList = queryClient.getQueryData(["subjectToAdd"]);
      queryClient.setQueryData(["subjectToAdd"], (old: SubToAdd[]) => [
        ...old,
        params.subject,
      ]);
      return { preSubjectList };
    },
    onSettled: async () => {
      //queryClient 리액트 쿼리 관련된 정보(캐시) 저장해주는 클래스 / invalidateQuries 지정된 queryKey로 시작되는 쿼리를 무효화 해주는 함수. 값이 없을 시 모두 무효화
      await queryClient.invalidateQueries({ queryKey: ["subjectToAdd"] });
      // ["subjectToAdd"]
      // ["subjectToAdd", userId, 3333]
    },
  });
};
export const useMutateDeleteSubject = () => {
  return useMutation({
    mutationFn: (params: Params) => deleteSubject(params.subject),
    onMutate: async (params: Params) => {
      await queryClient.cancelQueries({ queryKey: ["subjectToAdd"] });
      const preSubjectList = queryClient.getQueryData(["subjectToAdd"]);
      queryClient.setQueryData(["subjectToAdd"], (old: SubToAdd[]) => {
        old.filter((now) => now !== params.subject);
      });
      return { preSubjectList };
    },
    onSettled: async () => {
      await queryClient.invalidateQueries({ queryKey: ["subjectToAdd"] });
    },
  });
};
