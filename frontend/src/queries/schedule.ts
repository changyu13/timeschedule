import { useMutation, useQuery } from "@tanstack/react-query";
import { getSchedule, postSchedule } from "../api/schedule";
import { queryClient } from "../globals";

interface Params {
  userCredit: number;
}

export const useMutateSchedule = () => {
  return useMutation({
    mutationFn: (params: Params) => postSchedule(params.userCredit),
    onSettled: async () => {
      await queryClient.invalidateQueries({
        queryKey: ["subject"],
      });
      await queryClient.invalidateQueries({
        queryKey: ["schedule"],
      });
    },
  });
};

export const useQuerySchedule = () => {
  return useQuery({
    queryKey: ["schedule"],
    queryFn: getSchedule,
  });
};

//network탭을 관찰해서 백엔드 문제인지 프론트엔드 문제인지 확인
