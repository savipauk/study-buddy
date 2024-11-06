import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

import com.study_buddy.study_buddy.model.User;
import com.study_buddy.study_buddy.model.Group;
import com.study_buddy.study_buddy.model.Lesson;

@Embeddable
public class GroupMemberId implements Serializable {
    private Long memberId;
    private Long groupId;

    // Default constructor
    public GroupMemberId() {}

    public GroupMemberId(Long memberId, Long groupId) {
        this.memberId = memberId;
        this.groupId = groupId;
    }

    // Getters and setters
    public Long getMemberId() { return memberId; }
    public void setMemberId(Long memberId) { this.memberId = memberId; }

    public Long getGroupId() { return groupId; }
    public void setGroupId(Long groupId) { this.groupId = groupId; }

    // Override equals and hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GroupMemberId that = (GroupMemberId) o;
        return Objects.equals(memberId, that.memberId) && Objects.equals(groupId, that.groupId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(memberId, groupId);
    }
}
