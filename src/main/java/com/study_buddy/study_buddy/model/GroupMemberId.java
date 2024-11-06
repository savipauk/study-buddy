package com.study_buddy.study_buddy.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class GroupMemberId implements Serializable {

    @Column(name = "group_id")
    private Long groupId;

    @Column(name = "member_id")
    private Long memberId;

    public GroupMemberId() {
    }

    public GroupMemberId(Long groupId, Long memberId) {
        this.groupId = groupId;
        this.memberId = memberId;
    }

    // Getters and setters
    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        GroupMemberId that = (GroupMemberId) o;
        return Objects.equals(groupId, that.groupId) && Objects.equals(memberId, that.memberId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(groupId, memberId);
    }
}
