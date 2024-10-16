import { useMutation, useQuery } from "@tanstack/react-query";
import { getSchedule, postSchedule } from "../api/schedule";

interface Params {
  requiredList: string[];
  electiveList: string[];
  userCredit: number;
}

export const useMutateSchedule = () => {
  return useMutation({
    mutationFn: (params: Params) =>
      postSchedule(params.requiredList, params.electiveList, params.userCredit),
  });
};

export const useQuerySchedule = () => {
  return useQuery({
    queryKey: ["schdule"],
    queryFn: getSchedule,
  });
};
