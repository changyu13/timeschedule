package com.example.schedule.repository;

import com.example.schedule.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;

public interface SubjectRepository extends JpaRepository<Subject,String> {
    @Override
    ArrayList<Subject> findAll();
}
