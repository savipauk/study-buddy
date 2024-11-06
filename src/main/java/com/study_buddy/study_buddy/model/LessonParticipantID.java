package com.study_buddy.study_buddy.model;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class LessonParticipantID implements Serializable {
    private Long userId;
    private Long lessonId;

    // Constructors
    public LessonParticipantID() {}

    public LessonParticipantID(Long userId, Long lessonId) {
        this.userId = userId;
        this.lessonId = lessonId;
    }

    // Getters and Setters
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public Long getLessonId() { return lessonId; }
    public void setLessonId(Long lessonId) { this.lessonId = lessonId; }

    // hashCode and equals for composite key
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LessonParticipantID that = (LessonParticipantID) o;
        return Objects.equals(userId, that.userId) && Objects.equals(lessonId, that.lessonId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, lessonId);
    }
}
