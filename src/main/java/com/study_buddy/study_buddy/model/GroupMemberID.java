package com.study_buddy.study_buddy.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class GroupMemberID implements Serializable {
    
    @Column(name = "group_id", nullable = false)
    private Long groupId;

    @Column(name = "student_id", nullable = false)
    private Long studentId;

    public GroupMemberID() {}

    public GroupMemberID(Long studentId, Long groupId) {
        this.studentId = studentId;
        this.groupId = groupId;
    }

    public Long getMemberId() {
        return studentId;
    }
    
    public void setMemberId(Long studentId) {
        this.studentId = studentId;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

}
