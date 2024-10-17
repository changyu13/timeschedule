package com.example.schedule.schedule;

import com.example.schedule.entity.Schedule;

import java.util.ArrayList;

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
