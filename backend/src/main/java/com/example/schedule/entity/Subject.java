package com.example.schedule.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Subject {
    @Id
    private String subjectNo;
    @Column
    private String courseNo;
    @Column
    private String subjectName;
    @Column
    private String professor;
    @Column
    private Integer credit;
}
