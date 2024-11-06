package com.study_buddy.study_buddy.model;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class GroupMemberID implements Serializable {
    private Long memberId;
    private Long groupId;

    // Constructors
    public GroupMemberID() {}

    public GroupMemberID(Long memberId, Long groupId) {
        this.memberId = memberId;
        this.groupId = groupId;
    }

    // Getters and Setters
    public Long getMemberId() { return memberId; }
    public void setMemberId(Long memberId) { this.memberId = memberId; }

    public Long getGroupId() { return groupId; }
    public void setGroupId(Long groupId) { this.groupId = groupId; }

    // hashCode and equals for composite key
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GroupMemberID that = (GroupMemberID) o;
        return Objects.equals(memberId, that.memberId) && Objects.equals(groupId, that.groupId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(memberId, groupId);
    }
}
