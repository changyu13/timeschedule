package com.example.schedule.schedule;

import com.example.schedule.dto.RecommendedScheduleDto;
import com.example.schedule.entity.Schedule;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

public class MakeSchedule {
    private int userCredit;
    private List<Group> groups;
    private ArrayList<ArrayList<Schedule>> finishedSchedules;

    private int maxEmpty = 0;
    private int maxEmptyIdx = -1;
    private int minWait = 100;
    private int minWaitIdx = -1;
    private int laziestScoreBest = 0;
    private int laziestScoreIdx = -1;

    public MakeSchedule(List<Group> groups, int userCredit) {
        this.userCredit = userCredit;
        this.groups = groups;
        this.finishedSchedules = new ArrayList<>();

        ArrayList<ArrayList<Schedule>> timeSchedule = new ArrayList<>();
        for (int i = 0; i <= 6; i++)
            timeSchedule.add(new ArrayList<>());

        scheduleCheck(timeSchedule, 0, 0);
    }

    public RecommendedScheduleDto recommendSchedule() {
        System.out.println("userCredit:"+userCredit);
        return new RecommendedScheduleDto(
                maxEmptyIdx >= 0 ? finishedSchedules.get(maxEmptyIdx) : null,
                finishedSchedules.get(minWaitIdx),
                finishedSchedules.get(laziestScoreIdx));

    }

    private void scheduleCheck(ArrayList<ArrayList<Schedule>> timeSchedule, int depth, int sum) {
        if (sum > userCredit) {
            return;
        } else if (depth >= groups.size()) {
            if (sum == userCredit) {
                ArrayList<Schedule> courses = new ArrayList<>();
                for (List<Schedule> schedules : timeSchedule) {
                    courses.addAll(schedules);
                }
                System.out.println("추가");
                finishedSchedules.add(courses);
                // manyEmpty 저장하는 코드
                int emptyScore = ScoreCalc.emptyScore(timeSchedule);
                if (maxEmpty < emptyScore) {
                    maxEmpty = emptyScore;
                    maxEmptyIdx = finishedSchedules.size() - 1;
                }
                int waitScore = ScoreCalc.littleWaitScore(timeSchedule);
                if(waitScore < minWait){
                    minWait = waitScore;
                    minWaitIdx = finishedSchedules.size() - 1;
                }
                int laziestScore = ScoreCalc.laziestScore(courses);
                if(laziestScoreBest < laziestScore){
                    laziestScoreBest = laziestScore;
                    laziestScoreIdx = finishedSchedules.size()-1;
                }
                ////////
            }
            return;
        }

        ArrayList<GroupMember> subjectInGroup = groups.get(depth).getGroupMemberList();

        if (!groups.get(depth).isRequired()) {
            scheduleCheck(timeSchedule, depth + 1, sum);
        }
        for (int i = 0; i < subjectInGroup.size(); i++) {
            GroupMember subjectToAdd = subjectInGroup.get(i);
            List<Schedule> scheduleToAdd = subjectToAdd.getSchedules();
            boolean hasConflict = false;
            for (int j = 0; j < scheduleToAdd.size(); j++) {
                int dow = scheduleToAdd.get(j).getScheduleId().getDow();
                ArrayList<Schedule> todayTimeSchedule = timeSchedule.get(dow);
                for (int k = 0; k < todayTimeSchedule.size() && !hasConflict; k++) {
                    hasConflict = scheduleToAdd.get(j).doesConflict(todayTimeSchedule.get(k));
                }

                if (hasConflict) {
                    break;
                }
            }
            if (!hasConflict) {
                for (int j = 0; j < scheduleToAdd.size(); j++) {
                    int dow = scheduleToAdd.get(j).getScheduleId().getDow();
                    ArrayList<Schedule> todayTimeSchedule = timeSchedule.get(dow);
                    todayTimeSchedule.add(scheduleToAdd.get(j));
                }
                scheduleCheck(timeSchedule, depth + 1, sum + subjectToAdd.getCredit());
                for (int j = 0; j < scheduleToAdd.size(); j++) {
                    int dow = scheduleToAdd.get(j).getScheduleId().getDow();
                    ArrayList<Schedule> todayTimeSchedule = timeSchedule.get(dow);
                    todayTimeSchedule.remove(todayTimeSchedule.size() - 1);
                }
            }
        }
    }

}
