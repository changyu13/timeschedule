package com.example.schedule.dto;

import com.example.schedule.entity.Schedule;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RecommendedScheduleDto {
    private ArrayList<Schedule> manyEmpty;
    private ArrayList<Schedule> littleWait;
    private ArrayList<Schedule> noMorning;
}
