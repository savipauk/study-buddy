package com.study_buddy.study_buddy.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "GroupMembers")
public class GroupMember {

    @EmbeddedId
    private GroupMemberID id;

    @MapsId("studentId")
    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Student memberId;

    @MapsId("groupId")
    @ManyToOne
    @JoinColumn(name = "group_id", nullable = false)
    private StudyGroup groupId;

    @Column(name = "join_date", nullable = false)
    private LocalDateTime joinDate;

    public GroupMember() {}

    public GroupMember(Student memberId, StudyGroup groupId, LocalDateTime joinDate) {
        this.memberId = memberId;
        this.groupId = groupId;
        this.joinDate = joinDate;
        this.id = new GroupMemberID(memberId.getStudentId(),groupId.getGroupId());

    }

    public GroupMemberID getId() {
        return id;
    }

    public void setId(GroupMemberID id) {
        this.id = id;
    }

    public Student getMember() {
        return memberId;
    }

    public void setMember(Student memberId) {
        this.memberId = memberId;
    }

    public StudyGroup getGroup() {
        return groupId;
    }

    public void setGroup(StudyGroup groupId) {
        this.groupId = groupId;
    }

    public LocalDateTime getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(LocalDateTime joinDate) {
        this.joinDate = joinDate;
    }

    @Override
    public String toString() {
        return "GroupMember{" +
                "id=" + id +
                ", memberId=" + memberId +
                ", groupId=" + groupId +
                ", joinDate=" + joinDate +
                '}';
    }
}
