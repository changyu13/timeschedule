package com.example.schedule;

import com.example.schedule.dto.SubjectDto;
import com.example.schedule.entity.Subject;
import com.example.schedule.repository.SubjectRepository;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@SpringBootApplication
public class BackendApplication {
	public static void main(String[] args) throws CsvValidationException,IOException {
		SpringApplication.run(BackendApplication.class, args);
		CSVReader csvReader = new CSVReader(new FileReader("D:\\study\\timeschedule\\backend\\schedule.csv"));
		String[] line;
		String[] time;
		int noIdx=6;
		int nameIdx=7;
		int timeIdx=16;
		int professorIdx=17;
		int idx;
		int count=0;
		csvReader.readNext();
		while((line=csvReader.readNext())!=null){

			/*
			time = line[timeIdx].split(",");
			for(int i=0;i<time.length;i++){
				time[i]= time[i].trim();
				idx=time[i].indexOf("-");
				System.out.println(line[noIdx]+"+"+time[i].charAt(0));
				System.out.println(time[i].substring(idx-2,idx));
				System.out.println(time[i].substring(idx+1,idx+3));
			}*/

			//과목 넣기
			SubjectDto dto = new SubjectDto(line[noIdx],line[nameIdx],line[professorIdx]);
			Subject subject = dto.toEntity();
			System.out.println(subject.getSubjectNo()+"/"+subject.getSubjectName()+"/"+subject.getProfessor());

			count++;
			if(count==5){
				break;
			}
		}
	}
}
