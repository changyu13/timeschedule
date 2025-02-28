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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@ConditionalOnNotWebApplication
public class Update implements CommandLineRunner {
    @Autowired
    SubjectRepository subjectRepository;
    @Autowired
    ScheduleRepository scheduleRepository;

    //파싱해서 사용하자.
    private static final Pattern REGEX_TIME = Pattern.compile("([월화수목금토일])(\\d{2})(?:-(\\d{2}))?(?:\\(([^,\\n]*)\\))?");

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
        CSVReader csvReader = new CSVReader(new FileReader("schedule.csv"));
        String[] line;
        String[] time;
        int courseIdx = 4;
        int noIdx = 6;
        int nameIdx = 7;
        int creditIdx = 11;
        int timeIdx = 16;
        int professorIdx = 17;
        int idx;
        int count = 0;
        String[] scheduleList;
        csvReader.readNext();
        while ((line = csvReader.readNext()) != null) {
            //과목 넣기
            if (line[timeIdx].trim().isEmpty() || line[professorIdx].trim().isEmpty()) {
                System.out.println(line[noIdx] + " : 필드값이 없습니다.");
                continue;
            }
            else if(line[timeIdx].trim().equals("(e-러닝)")){
                ScheduleId id = new ScheduleId(line[noIdx], dayOfWeek('월'));
                ScheduleDto scheduleDto = new ScheduleDto(id, 30,30);
                Schedule schedule = scheduleDto.toEntity();
                scheduleRepository.save(schedule);

                SubjectDto subjectDto = new SubjectDto(line[noIdx],line[courseIdx], line[nameIdx].trim(), line[professorIdx].trim(),(int)Double.parseDouble(line[creditIdx]));
                Subject subject = subjectDto.toEntity();
                System.out.println(subject.getSubjectNo() + "/" + subject.getSubjectName() + "/" + subject.getProfessor());
                subjectRepository.save(subject);
                System.out.println(line[noIdx] + " : e러닝");
                continue;
            }
            scheduleList = line[timeIdx].split(",");
            for (int i = 0; i < scheduleList.length; i++) {
                String s = scheduleList[i].trim();
                Matcher matcher = REGEX_TIME.matcher(s);
                String day="";
                String startTime="";
                String endTime="";
                String room="";
                if(matcher.find()){
                    day = matcher.group(1);
                    startTime = matcher.group(2);
                    endTime= matcher.group(3);
                    room = matcher.group(4);
                }else{
                    System.out.println("요주의 인물 : "+ s);
                }
                //가독성이 떨어지니 요일을 받고 숫자를 반환하는 함수 생성
                ScheduleId id = new ScheduleId(line[noIdx], dayOfWeek(s.charAt(0)));
                ScheduleDto scheduleDto = (endTime == null)? new ScheduleDto(id, Integer.parseInt(startTime), Integer.parseInt(startTime))
                        : new ScheduleDto(id, Integer.parseInt(startTime), Integer.parseInt(endTime));
                Schedule schedule = scheduleDto.toEntity();
                scheduleRepository.save(schedule);
            }
            SubjectDto subjectDto = new SubjectDto(line[noIdx],line[courseIdx], line[nameIdx].trim(), line[professorIdx].trim(),(int)Double.parseDouble(line[creditIdx]));
            Subject subject = subjectDto.toEntity();
            System.out.println(subject.getSubjectNo() + "/" + subject.getSubjectName() + "/" + subject.getProfessor());
            subjectRepository.save(subject);

        }
    }
}
