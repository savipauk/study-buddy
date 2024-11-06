package com.study_buddy.study_buddy.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class GroupMembers {
    @EmbeddedId
    private GroupMemberID id;

    @MapsId("memberId")
    @ManyToOne
    @JoinColumn(name = "member_id")
    private User member;

    @MapsId("groupId")
    @ManyToOne
    @JoinColumn(name = "group_id")
    private StudyGroup group;

    @Column(name = "join_date")
    private LocalDateTime joinDate;

    // Getters and setters
    public GroupMemberID getId() { return id; }
    public void setId(GroupMemberID id) { this.id = id; }

    public User getMember() { return member; }
    public void setMember(User member) { this.member = member; }

    public StudyGroup getGroup() { return group; }
    public void setGroup(StudyGroup group) { this.group = group; }

    public LocalDateTime getJoinDate() { return joinDate; }
    public void setJoinDate(LocalDateTime joinDate) { this.joinDate = joinDate; }
}
