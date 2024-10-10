package com.example.schedule.schedule;

import com.example.schedule.entity.Schedule;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class GroupMember {
    private int credit;
    private List<Schedule> schedules;
}
