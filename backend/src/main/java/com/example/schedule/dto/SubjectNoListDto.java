package com.example.schedule.dto;

import com.example.schedule.entity.Schedule;
import com.example.schedule.entity.Subject;
import lombok.*;

import java.util.ArrayList;
@Data
public class SubjectNoListDto {
    private ArrayList<String> requiredSubjects;
    private ArrayList<String> electiveSubjects;
    int userCredit;

}
