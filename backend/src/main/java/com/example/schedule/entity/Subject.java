package com.example.schedule.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Entity
@AllArgsConstructor
@Getter
public class Subject {
    @Id
    private String subjectNo;
    @Column
    private String subjectName;
    @Column
    private String professor;
}
