package com.example.schedule.api;

import com.example.schedule.dto.SubjectNoListDto;
import com.example.schedule.embeddedId.ScheduleId;
import com.example.schedule.entity.Schedule;
import com.example.schedule.entity.Subject;
import com.example.schedule.repository.ScheduleRepository;
import com.example.schedule.repository.SubjectRepository;
import com.example.schedule.schedule.Group;
import com.example.schedule.schedule.MakeSchedule;
import com.example.schedule.service.ScheduleMakeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class SubjectController {
    @Autowired
    SubjectRepository subjectRepository;
    @Autowired
    ScheduleRepository scheduleRepository;
    @Autowired
    ScheduleMakeService scheduleMakeService;

    @GetMapping("/api/subject")
    public ResponseEntity<List<Subject>> getSubject(){
        List<Subject> subjectList = subjectRepository.findAll();

        if(subjectList.size()==0){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } else{
            return ResponseEntity.status(HttpStatus.OK).body(subjectList);
        }
    }
    /*@GetMapping("/api/schedule")
    public ResponseEntity<List<Schedule>> getSchedule(){
        List<Schedule> scheduleList = scheduleRepository.findAll();

        if(scheduleList.size()==0){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        else{
            return ResponseEntity.status(HttpStatus.OK).body(scheduleList);
        }
    }*/
    @PostMapping("/api/courses/add")
    public ResponseEntity<List<Schedule>> addCourse(@RequestBody SubjectNoListDto dto){
        System.out.println(dto);

        List<Group> groups = scheduleMakeService.f(dto.getRequiredSubjects(),dto.getElectiveSubjects());

        MakeSchedule mySchdule = new MakeSchedule(groups);
        //visual studio에서 requiredList, electiveList dto 보내기
        return ResponseEntity.status(HttpStatus.OK).body(mySchdule.getFinishedSchedules().get(0));
    }
}
