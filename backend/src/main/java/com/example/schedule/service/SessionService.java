package com.example.schedule.service;

import com.example.schedule.schedule.MakeSchedule;
import jakarta.servlet.http.HttpSession;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SessionService {
    // 스프링부트를 웹서버로서 실행할때 자동으로 만들어주는 bean이 update로 실행하면 없어서 HttpSession에 할당 불가함.
    @Autowired(required = false)
    private HttpSession httpSession;

    private static final String SCHEDULE = "SCHEDULE";

    public void setMakeSchedule(MakeSchedule mySchedule) {
        httpSession.setAttribute(SCHEDULE, mySchedule);
    }

    public MakeSchedule getMakeSchedule() {
        return (MakeSchedule) httpSession.getAttribute(SCHEDULE);
    }
}