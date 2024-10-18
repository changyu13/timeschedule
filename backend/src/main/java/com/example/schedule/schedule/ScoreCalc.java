package com.example.schedule.schedule;

import com.example.schedule.entity.Schedule;

import java.util.ArrayList;
import java.util.Collections;

public class ScoreCalc {
    public static int emptyScore(ArrayList<ArrayList<Schedule>> timeSchedule){
        int score =0;
        for(int i=1; i<timeSchedule.size();i++){
            if(timeSchedule.get(i).isEmpty()){
                score++;
            }
        }
        return score;
    }

    public static int littleWaitScore(ArrayList<ArrayList<Schedule>> timeSchedule){
        int waitScore = 0;
        for (int i=1; i<timeSchedule.size();i++){
            Collections.sort(timeSchedule.get(i));
            for(int j=1; j<timeSchedule.get(i).size(); j++){
                waitScore += timeSchedule.get(i).get(j).getStartTime()-timeSchedule.get(i).get(j-1).getEndTime();
            }
        }
        return waitScore;
    }
    // 할일 리스트
    // 1.두번째 점수계산 테마 설정
    // 2. requiredList와 electiveList 하나로 합치기 boolean required 변수 추가
    // 3. 장바구니에 담고 지울 때 mutate로 추가 & 삭제 요청 api
    

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
