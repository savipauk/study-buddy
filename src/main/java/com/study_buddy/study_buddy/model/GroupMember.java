package com.study_buddy.study_buddy.model;

import jakarta.persistence.*;

@Entity
@Table(name = "GroupMembers")
public class GroupMember {

    @EmbeddedId
    private GroupMemberId id = new GroupMemberId();

    @ManyToOne
    @MapsId("groupId")
    @JoinColumn(name = "group_id")
    private StudyGroup studyGroup;

    @ManyToOne
    @MapsId("memberId")
    @JoinColumn(name = "member_id")
    private Student student;

    public GroupMember() {
    }

    public GroupMember(StudyGroup studyGroup, Student student) {
        this.studyGroup = studyGroup;
        this.student = student;
        this.id = new GroupMemberId(studyGroup.getId(), student.getId());
    }

    // Getters and setters
    public GroupMemberId getId() {
        return id;
    }

    public void setId(GroupMemberId id) {
        this.id = id;
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
