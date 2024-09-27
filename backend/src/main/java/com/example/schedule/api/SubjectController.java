package com.example.schedule.api;

import com.example.schedule.entity.Subject;
import com.example.schedule.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.lang.model.type.ArrayType;
import java.util.ArrayList;

@RestController
public class SubjectController {
    @Autowired
    SubjectRepository subjectRepository;
    @GetMapping("/api/subject")
    public ResponseEntity<ArrayList<Subject>> getSubject(){
        ArrayList<Subject> subjectList = new ArrayList<>();
        subjectList = subjectRepository.findAll();

        if(subjectList==null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } else{
            return ResponseEntity.status(HttpStatus.OK).body(subjectList);
        }
    }
}
