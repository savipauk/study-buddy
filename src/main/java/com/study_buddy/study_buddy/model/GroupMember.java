import jakarta.persistence.*;
import java.time.LocalDateTime;

import com.study_buddy.study_buddy.model.User;
import com.study_buddy.study_buddy.model.Group;
import com.study_buddy.study_buddy.model.Lesson;
import com.study_buddy.study_buddy.model.GroupMembersID;


@Entity
public class GroupMembers {
    @EmbeddedId
    private GroupMemberId id;

    @MapsId("memberId")
    @ManyToOne
    @JoinColumn(name = "member_id")
    private User member;

    @MapsId("groupId")
    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;

    @Column(name = "join_date")
    private LocalDateTime joinDate;

    // Getters and setters
    public GroupMemberId getId() { return id; }
    public void setId(GroupMemberId id) {
        this.id = id;
    }

    public User getMember() { return member; }
    public void setMember(User member) {
        this.member = member;
    }

    public Group getGroup() {
        return group;
    }
    public void setGroup(Group group) { 
        this.group = group;
    }

    public LocalDateTime getJoinDate() {
        return joinDate;
    }
    public void setJoinDate(LocalDateTime joinDate) {
        this.joinDate = joinDate;
    }
}
