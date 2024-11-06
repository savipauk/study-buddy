package com.study_buddy.study_buddy.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class LessonParticipantId implements Serializable {

    @Column(name = "lesson_id")
    private Long lessonId;

    @Column(name = "participant_id")
    private Long participantId;

    public LessonParticipantId() {
    }

    public LessonParticipantId(Long lessonId, Long participantId) {
        this.lessonId = lessonId;
        this.participantId = participantId;
    }

    // Getters and setters
    public Long getLessonId() {
        return lessonId;
    }

    public void setLessonId(Long lessonId) {
        this.lessonId = lessonId;
    }

    public Long getParticipantId() {
        return participantId;
    }

    public void setParticipantId(Long participantId) {
        this.participantId = participantId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        LessonParticipantId that = (LessonParticipantId) o;
        return Objects.equals(lessonId, that.lessonId) && Objects.equals(participantId, that.participantId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lessonId, participantId);
    }
}
