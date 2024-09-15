package com.example.schedule.dto;

import com.example.schedule.entity.Subject;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class SubjectDto {
    private String subjectNo;
    private String subjectName;
    private String professor;

    public Subject toEntity(){
        return new Subject(subjectNo,subjectName,professor);
    }
}
