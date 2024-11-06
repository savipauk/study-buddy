import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

import com.study_buddy.study_buddy.model.User;
import com.study_buddy.study_buddy.model.Group;
import com.study_buddy.study_buddy.model.Lesson;

@Embeddable
public class LessonParticipantId implements Serializable {
    private Long userId;
    private Long lessonId;

    // Default constructor
    public LessonParticipantId() {}

    public LessonParticipantId(Long userId, Long lessonId) {
        this.userId = userId;
        this.lessonId = lessonId;
    }

    // Getters and setters
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public Long getLessonId() { return lessonId; }
    public void setLessonId(Long lessonId) { this.lessonId = lessonId; }

    // Override equals and hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LessonParticipantId that = (LessonParticipantId) o;
        return Objects.equals(userId, that.userId) && Objects.equals(lessonId, that.lessonId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, lessonId);
    }
}
