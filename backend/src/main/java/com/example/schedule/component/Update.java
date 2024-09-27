package com.example.schedule.component;

import com.example.schedule.dto.ScheduleDto;
import com.example.schedule.dto.SubjectDto;
import com.example.schedule.embeddedId.ScheduleId;
import com.example.schedule.entity.Schedule;
import com.example.schedule.entity.Subject;
import com.example.schedule.repository.ScheduleRepository;
import com.example.schedule.repository.SubjectRepository;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnNotWebApplication;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

@Component
@ConditionalOnNotWebApplication
public class Update implements CommandLineRunner {
    @Autowired
    SubjectRepository subjectRepository;
    @Autowired
    ScheduleRepository scheduleRepository;

    public int dayOfWeek(char d) {
        switch (d) {
            case ('월'):
                return 1;
            case ('화'):
                return 2;
            case ('수'):
                return 3;
            case ('목'):
                return 4;
            case ('금'):
                return 5;
            case ('토'):
                return 6;
            default:
                return 0;
        }
    }

    @Override
    public void run(String... args) throws CsvValidationException, IOException {
        CSVReader csvReader = new CSVReader(new FileReader("D:\\study\\timeschedule\\backend\\schedule.csv"));
        String[] line;
        String[] time;
        int courseIdx = 4;
        int noIdx = 6;
        int nameIdx = 7;
        int creditIdx = 12;
        int timeIdx = 16;
        int professorIdx = 17;
        int idx;
        int count = 0;
        String[] scheduleList;
        csvReader.readNext();
        while ((line = csvReader.readNext()) != null) {
            //과목 넣기
            if (line[timeIdx].trim().isEmpty() || line[timeIdx].contains("e") || line[professorIdx].trim().isEmpty()) {
                System.out.println(noIdx + " : 필드값이 없습니다.");
                continue;
            }
            scheduleList = line[timeIdx].split(",");
            for (int i = 0; i < scheduleList.length; i++) {
                String s = scheduleList[i].trim();
                //가독성이 떨어지니 요일을 받고 숫자를 반환하는 함수 생성
                ScheduleId id = new ScheduleId(line[noIdx], dayOfWeek(s.charAt(0)));
                if (s.charAt(3) != '-') {
                    continue;
                }
                ScheduleDto schedulteDto = new ScheduleDto(id, Integer.parseInt(s.substring(1, 3)), Integer.parseInt(s.substring(4, 6)));
                Schedule schedule = schedulteDto.toEntity();
                scheduleRepository.save(schedule);
            }
            SubjectDto subjectDto = new SubjectDto(line[noIdx],line[courseIdx], line[nameIdx].trim(), line[professorIdx].trim(),(int)Double.parseDouble(line[creditIdx]));
            Subject subject = subjectDto.toEntity();
            System.out.println(subject.getSubjectNo() + "/" + subject.getSubjectName() + "/" + subject.getProfessor());
            subjectRepository.save(subject);

        }
    }
}
