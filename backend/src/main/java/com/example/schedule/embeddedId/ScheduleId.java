package com.example.schedule.embeddedId;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Data
public class ScheduleId implements Serializable {
    @Column
    private String subjectNo;
    @Column
    private Integer dow;
}
