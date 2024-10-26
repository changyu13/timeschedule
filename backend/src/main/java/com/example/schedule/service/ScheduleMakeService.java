package com.example.schedule.service;

import com.example.schedule.dto.SubToAddDto;
import com.example.schedule.embeddedId.ScheduleId;
import com.example.schedule.entity.Schedule;
import com.example.schedule.entity.Subject;
import com.example.schedule.repository.ScheduleRepository;
import com.example.schedule.repository.SubjectRepository;
import com.example.schedule.schedule.Group;
import com.example.schedule.schedule.GroupMember;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ScheduleMakeService {
    @Autowired
    ScheduleRepository scheduleRepository;
    @Autowired
    SubjectRepository subjectRepository;

    public ArrayList<Group> makeGroup(Collection<SubToAddDto> subjectList) {

        Map<String, ArrayList<GroupMember>> map = new HashMap<>();
        ArrayList<Group> groups= new ArrayList<>();
        
        //필수 과목이 들어있는 Group 생성후 Groups에 추가
        for (SubToAddDto now : subjectList) {
            Optional<Subject> target = subjectRepository.findById(now.getSubjectNo());
            if (target.isEmpty()) {
                System.out.println(now.getSubjectNo() + "가 DB에 존재하지 않음");
                continue;
            }

            ScheduleId scheduleId = new ScheduleId(target.get().getSubjectNo(), null);
            Schedule schedule = new Schedule(scheduleId, null, null);
            List<Schedule> scheduleList = scheduleRepository.findAll(Example.of(schedule));
            if (scheduleList.isEmpty()) {
                System.out.println("sheduleId : "+ scheduleId);
                throw new RuntimeException("해당 schedule을 찾을 수 없음");
            }

            GroupMember groupMem = new GroupMember(target.get().getCredit(), scheduleList,now.isRequired());
            if (!map.containsKey(target.get().getCourseNo())) {
                ArrayList<GroupMember> groupCandidate = new ArrayList<>();
                groupCandidate.add(groupMem);
                map.put(target.get().getCourseNo(), groupCandidate);
            } else {
                map.get(target.get().getCourseNo()).add(groupMem);
            }
        }

        for(String key: map.keySet()){
            boolean required = map.get(key).get(0).isRequired();
            Group group = new Group(required,map.get(key));
            groups.add(group);
        }
        System.out.println(groups);
        return groups;
    }
}