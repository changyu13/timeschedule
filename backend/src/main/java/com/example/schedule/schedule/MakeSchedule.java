package com.example.schedule.schedule;

import com.example.schedule.entity.Schedule;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
public class MakeSchedule {
    private int targetCredit;
    private List<Group> groups;
    private ArrayList<ArrayList<Schedule>> finishedSchedules;

    public MakeSchedule(List<Group> groups) {
        this.targetCredit = 9;
        this.groups = groups;
        this.finishedSchedules = new ArrayList<>();

        ArrayList<ArrayList<Schedule>> timeSchedule = new ArrayList<>();
        for (int i = 0; i <= 7; i++)
            timeSchedule.add(new ArrayList<>());

        scheduleCheck(timeSchedule, 0, 0);
    }

    private void scheduleCheck(ArrayList<ArrayList<Schedule>> timeSchedule, int depth, int sum) {
        if (sum > targetCredit) {
            return;
        } else if (depth >= groups.size()) {
            if (sum == targetCredit) {
                ArrayList<Schedule> courses = new ArrayList<>();
                for (List<Schedule> schedules : timeSchedule) {
                    for (Schedule schedule : schedules) {
                        courses.add(schedule);
                    }
                }
                finishedSchedules.add(courses);
            }
            return;
        }

        ArrayList<GroupMember> subjectInGroup = groups.get(depth).getGroupMemberList();

        if (!groups.get(depth).isRequired()) {
            scheduleCheck(timeSchedule, depth + 1, sum);
        }

        for (int i = 0; i < subjectInGroup.size(); i++) {
            GroupMember subjectToAdd = subjectInGroup.get(i);
            Schedule scheduleToAdd = subjectToAdd.getSchedules().get(0);
            int dow = scheduleToAdd.getScheduleId().getDow();
            ArrayList<Schedule> todayTimeSchedule = timeSchedule.get(dow);

            boolean hasConflict = false;
            for (int j = 0; j < todayTimeSchedule.size() && !hasConflict; j++) {
                hasConflict = scheduleToAdd.doesConflict(todayTimeSchedule.get(j));
            }

            if (!hasConflict) {
                todayTimeSchedule.add(scheduleToAdd);
                scheduleCheck(timeSchedule, depth + 1, sum + subjectToAdd.getCredit());
                todayTimeSchedule.remove(todayTimeSchedule.size() - 1);
            }
        }
    }
}
