import { useMutation, useQuery } from "@tanstack/react-query";
import { getSchedule, postSchedule } from "../api/schedule";

interface Params {
  userCredit: number;
}

export const useMutateSchedule = () => {
  return useMutation({
    mutationFn: (params: Params) => postSchedule(params.userCredit),
  });
};

export const useQuerySchedule = () => {
  return useQuery({
    queryKey: ["schdule"],
    queryFn: getSchedule,
  });
};
