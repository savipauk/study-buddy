import jakarta.persistence.*;
import java.time.LocalDateTime;

import com.study_buddy.study_buddy.model.User;
import com.study_buddy.study_buddy.model.Group;
import com.study_buddy.study_buddy.model.Lesson;
import com.study_buddy.study_buddy.model.LessonParticipantsId;

@Entity
public class LessonParticipants {
    @EmbeddedId
    private LessonParticipantId id;

    @MapsId("userId")
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @MapsId("lessonId")
    @ManyToOne
    @JoinColumn(name = "lesson_id")
    private Lesson lesson;

    @Column(name = "participation_date")
    private LocalDateTime participationDate;

    // Getters and setters
    public LessonParticipantId getId() {
        return id;
    }
    public void setId(LessonParticipantId id) {
        this.id = id;
    }

    public User getUser() { return user; }
    public void setUser(User user) {
        this.user = user;
    }

    public Lesson getLesson() { return lesson; }
    public void setLesson(Lesson lesson) {
        this.lesson = lesson;
        }

    public LocalDateTime getParticipationDate() { return participationDate; }
    public void setParticipationDate(LocalDateTime participationDate) {
        this.participationDate = participationDate;
        }
}
