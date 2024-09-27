package com.example.schedule.entity;

import com.example.schedule.embeddedId.ScheduleId;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Schedule {
    @EmbeddedId
    private ScheduleId id;
    @Column
    private Integer startTime;
    @Column
    private Integer endTime;
}
