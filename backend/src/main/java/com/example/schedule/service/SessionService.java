package com.example.schedule.service;

import com.example.schedule.schedule.MakeSchedule;
import jakarta.servlet.http.HttpSession;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SessionService {
    @Autowired
    private HttpSession httpSession;

    private static final String SCHEDULE = "SCHEDULE";

    public void setMakeSchedule(MakeSchedule mySchedule) {
        httpSession.setAttribute(SCHEDULE, mySchedule);
    }

    public MakeSchedule getMakeSchedule() {
        return (MakeSchedule) httpSession.getAttribute(SCHEDULE);
    }
}
