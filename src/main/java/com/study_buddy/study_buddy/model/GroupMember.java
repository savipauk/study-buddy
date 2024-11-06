package com.study_buddy.study_buddy.model;

import jakarta.persistence.*;

@Entity
@Table(name = "GroupMembers")
public class GroupMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_id")
    private Long groupMemberId;

    @ManyToOne
    @MapsId("group_id")
    @JoinColumn(name = "group_id")
    private StudyGroup studyGroup;

    @ManyToOne
    @MapsId("member_id")
    @JoinColumn(name = "member_id")
    private Student student;

    public GroupMember() {}

    public GroupMember(Long groupMemberId, StudyGroup studyGroup, Student student) {
        this.groupMemberId = groupMemberId;
        this.studyGroup = studyGroup;
        this.student = student;
    }

    public Long getGroupMemberId() {
        return groupMemberId;
    }

    public void setGroupMemberId(Long groupMemberId) {
        this.groupMemberId = groupMemberId;
    }

    public StudyGroup getStudyGroup() {
        return studyGroup;
    }

    public void setStudyGroup(StudyGroup studyGroup) {
        this.studyGroup = studyGroup;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }
}

