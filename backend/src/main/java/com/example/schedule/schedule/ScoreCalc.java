package com.example.schedule.schedule;

import com.example.schedule.entity.Schedule;

import java.util.ArrayList;
import java.util.Collections;

public class ScoreCalc {
    public static int emptyScore(ArrayList<ArrayList<Schedule>> timeSchedule) {
        int score = 0;
        for (int i = 1; i < timeSchedule.size(); i++) {
            if (timeSchedule.get(i).isEmpty()) {
                score++;
            }
        }
        return score;
    }

    public static int littleWaitScore(ArrayList<ArrayList<Schedule>> timeSchedule) {
        int waitScore = 0;
        for (int i = 1; i < timeSchedule.size(); i++) {
            ArrayList<Schedule> dowTimeSchedule = new ArrayList<>(timeSchedule.get(i));
            Collections.sort(dowTimeSchedule);
            for (int j = 1; j < dowTimeSchedule.size(); j++) {
                waitScore += dowTimeSchedule.get(j).getStartTime() - dowTimeSchedule.get(j - 1).getEndTime();
                //저녁 시간& 점심시간이 껴 있을 시 더 낮은 점수 부여. 이게 옳은 것인가? 그럼 아침 시간도 배정해줘야하는가?
                if((dowTimeSchedule.get(j).getStartTime()>=9 && dowTimeSchedule.get(j-1).getEndTime() <= 6)||
                        (dowTimeSchedule.get(j).getStartTime()>=19 && dowTimeSchedule.get(j-1).getEndTime() <= 17)){
                    waitScore -= 2;
                }
            }
        }
        return waitScore;
    }
    // 할일 리스트
    // 1.두번째 점수계산 테마 설정


    public static int laziestScore(ArrayList<Schedule> schedule) {
        int lazyScore = 30;
        for (int i = 0; i < schedule.size(); i++) {
            if (schedule.get(i).getStartTime() < lazyScore) {
                lazyScore = schedule.get(i).getStartTime();
            }
        }
        return lazyScore;
    }
}
