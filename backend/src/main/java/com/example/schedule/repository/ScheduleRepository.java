package com.example.schedule.repository;

import com.example.schedule.embeddedId.ScheduleId;
import com.example.schedule.entity.Schedule;
import io.micrometer.observation.annotation.Observed;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;

public interface ScheduleRepository extends JpaRepository<Schedule, ScheduleId> {
}
