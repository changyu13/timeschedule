package com.example.schedule.dto;

import com.example.schedule.entity.Subject;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class SubjectDto {
    private String subjectNo;
    private String courseNo;
    private String subjectName;
    private String professor;
    private Integer credit;
    public Subject toEntity(){
        return new Subject(subjectNo,courseNo,subjectName,professor,credit);
    }
}
