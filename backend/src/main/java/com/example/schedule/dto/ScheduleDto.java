package com.example.schedule.dto;

import com.example.schedule.entity.Schedule;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ScheduleDto {
    private Integer subjectNo;
    private Integer dow;
    private Integer startTime;
    private Integer endTime;

    public Schedule toEntity(){
        return new Schedule(subjectNo,dow,startTime,endTime);
    }
}
