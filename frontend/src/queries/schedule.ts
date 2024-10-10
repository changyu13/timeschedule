import { useQuery } from "@tanstack/react-query";
import { fetchSchedule } from "../api/schedule";

export const useQuerySchedule = () => {
  return useQuery({
    queryKey: ["schedule"],
    queryFn: fetchSchedule,
  });
};
