package com.example.schedule.dto;

import com.example.schedule.embeddedId.ScheduleId;
import com.example.schedule.entity.Schedule;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ScheduleDto {
    private ScheduleId id;
    private Integer startTime;
    private Integer endTime;

    public Schedule toEntity(){
        return new Schedule(id,startTime,endTime);
    }
}
