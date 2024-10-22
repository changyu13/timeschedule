package com.example.schedule.api;

import com.example.schedule.dto.RecommendedScheduleDto;
import com.example.schedule.dto.SubToAddDto;
import com.example.schedule.dto.UserCreditDto;
import com.example.schedule.entity.Subject;
import com.example.schedule.repository.ScheduleRepository;
import com.example.schedule.repository.SubjectRepository;
import com.example.schedule.schedule.Group;
import com.example.schedule.schedule.MakeSchedule;
import com.example.schedule.service.ScheduleMakeService;
import com.example.schedule.service.SessionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
public class SubjectController {
    @Autowired
    SubjectRepository subjectRepository;
    @Autowired
    ScheduleRepository scheduleRepository;
    @Autowired
    ScheduleMakeService scheduleMakeService;
    @Autowired
    SessionService sessionService;

    @GetMapping("/api/subject")
    public ResponseEntity<List<Subject>> getSubject() {
        List<Subject> subjectList = subjectRepository.findAll();

        if (subjectList.size() == 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(subjectList);
        }
    }
    @PostMapping("/api/subject")
    public void addSubject(@RequestBody SubToAddDto dto){
        System.out.println("dto:"+dto);
        sessionService.getSubjectList().add(dto);
    }
    @DeleteMapping("/api/subject")
    public void deleteSubject(@RequestBody SubToAddDto dto){
        sessionService.getSubjectList().remove(dto);
    }
    @GetMapping("/api/subjectToAdd")
    public ResponseEntity<HashSet<SubToAddDto>> getSubjectToAddList(){
        return ResponseEntity.status(HttpStatus.OK).body(sessionService.getSubjectList());
    }

    @PostMapping("/api/schedule/create")
    public void makeMySchedule(@RequestBody UserCreditDto credit) {
        List<Group> groups = scheduleMakeService.makeGroup(sessionService.getSubjectList());
        System.out.println("groups:"+groups);
        //UserCreditDto 완성, 프론트엔드에서는 학점만 넣어서 api 호출
        MakeSchedule mySchdule = new MakeSchedule(groups,credit.getUserCredit());
        sessionService.setMakeSchedule(mySchdule);
        System.out.println(mySchdule);
    }

    //각 종목을 담고있는 리스트 3개가 들어간 dto 만들고 반환해주기
    @GetMapping("/api/schedule/get")
    public ResponseEntity<RecommendedScheduleDto> getSchedule() {
        MakeSchedule schedule = sessionService.getMakeSchedule();
        if (schedule == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        RecommendedScheduleDto recommendedSchedule = schedule.recommendSchedule();
        return ResponseEntity.status(HttpStatus.OK).body(recommendedSchedule);
    }
}
