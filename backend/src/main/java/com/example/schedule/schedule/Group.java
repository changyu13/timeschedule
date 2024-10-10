package com.example.schedule.schedule;

import lombok.*;

import java.util.ArrayList;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class Group {
    private boolean required;
    private ArrayList<GroupMember> groupMemberList;

}
