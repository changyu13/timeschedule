package com.example.schedule.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Objects;

@Setter
@Getter
@ToString
public class SubToAddDto {
    private String subjectNo;
    private boolean required;

    @Override
    public boolean equals(Object other){
        if(this==other) return true;
        if(!(other instanceof SubToAddDto)) return false;
        SubToAddDto dto = (SubToAddDto) other;
        return Objects.equals(this.subjectNo,dto.subjectNo);
    }
    @Override
    public int hashCode(){
        return Objects.hashCode(subjectNo);
    }
}
