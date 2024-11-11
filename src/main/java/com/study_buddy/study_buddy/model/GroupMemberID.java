package com.study_buddy.study_buddy.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class GroupMemberID implements Serializable {
    
    @Column(name = "group_id", nullable = false)
    private Long groupId;

    @Column(name = "member_id", nullable = false)
    private Long memberId;

    public GroupMemberID() {}

    public GroupMemberID(Long memberId, Long groupId) {
        this.memberId = memberId;
        this.groupId = groupId;
    }

    public Long getMemberId() {
        return memberId;
    }
    
    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

}
