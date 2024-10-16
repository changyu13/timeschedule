package com.example.schedule.api;

import com.example.schedule.dto.RecommendedScheduleDto;
import com.example.schedule.dto.SubjectNoListDto;
import com.example.schedule.entity.Subject;
import com.example.schedule.repository.ScheduleRepository;
import com.example.schedule.repository.SubjectRepository;
import com.example.schedule.schedule.Group;
import com.example.schedule.schedule.MakeSchedule;
import com.example.schedule.service.ScheduleMakeService;
import com.example.schedule.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
    @PostMapping("/api/schedule/create")
    public void addCourse(@RequestBody SubjectNoListDto dto) {
        System.out.println(dto);

        List<Group> groups = scheduleMakeService.f(dto.getRequiredSubjects(), dto.getElectiveSubjects());

        MakeSchedule mySchdule = new MakeSchedule(groups,dto.getUserCredit());
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
        if(schedule.getMaxEmptyIdx()==-1){
            RecommendedScheduleDto recommendedSchedule = new RecommendedScheduleDto(null,schedule.getFinishedSchedules().get(1),schedule.getFinishedSchedules().get(2));
            return ResponseEntity.status(HttpStatus.OK).body(recommendedSchedule);
        }
        RecommendedScheduleDto recommendedSchedule = new RecommendedScheduleDto(schedule.getFinishedSchedules().get(schedule.getMaxEmptyIdx()),schedule.getFinishedSchedules().get(1),schedule.getFinishedSchedules().get(2));
        return ResponseEntity.status(HttpStatus.OK).body(recommendedSchedule);
    }
}
