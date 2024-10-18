package com.example.schedule.entity;

import com.example.schedule.embeddedId.ScheduleId;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Schedule implements Comparable<Schedule> {
    @EmbeddedId
    private ScheduleId scheduleId;
    @Column
    private Integer startTime;
    @Column
    private Integer endTime;

    public boolean doesConflict(Schedule other) {
        return (other.getStartTime() <= startTime && startTime < other.getEndTime())
                || (other.getStartTime() < endTime && endTime <= other.getEndTime());
    }

    @Override
    public int compareTo(Schedule other){
        return Integer.compare(startTime, other.startTime);
    }
}
