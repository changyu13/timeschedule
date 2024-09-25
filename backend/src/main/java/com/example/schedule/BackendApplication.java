package com.example.schedule;

import com.example.schedule.dto.SubjectDto;
import com.example.schedule.entity.Subject;
import com.example.schedule.repository.SubjectRepository;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
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
		SpringApplicationBuilder app = new SpringApplicationBuilder(BackendApplication.class);
		if(args.length==0){
			app.web(WebApplicationType.SERVLET);
		}
		else{
			app.web(WebApplicationType.NONE);
		}
		app.run(args);
	}
}
