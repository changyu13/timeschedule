package com.example.schedule.schedule;

import com.example.schedule.entity.Schedule;

import java.util.ArrayList;

public class ScoreCalc {
    public static ArrayList<Schedule> laziestSchedule(ArrayList<ArrayList<Schedule>> schedules){
        int latestTime = 0;
        int lazyIdx=0;
        for(int i=0; i< schedules.size(); i++){
            int earliestPeriod = 30;
            for(int j=0; j<schedules.get(i).size(); j++){
                if(schedules.get(i).get(j).getStartTime()< earliestPeriod){
                    earliestPeriod= schedules.get(i).get(j).getStartTime();
                }
            }
            if(earliestPeriod>latestTime){
                latestTime= earliestPeriod;
                lazyIdx=i;
            }

        }
        return schedules.get(lazyIdx);
    }
}
