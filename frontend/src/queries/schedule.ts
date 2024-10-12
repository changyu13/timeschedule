import { useMutation, useQuery } from "@tanstack/react-query";
import { getSchedule, postSchedule } from "../api/schedule";

interface Params {
  requiredList: string[];
  electiveList: string[];
}

export const useMutateSchedule = () => {
  return useMutation({
    mutationFn: (params: Params) =>
      postSchedule(params.requiredList, params.electiveList),
  });
};

export const useQuerySchedule = () => {
  return useQuery({
    queryKey: ["schdule"],
    queryFn: getSchedule,
  });
};
