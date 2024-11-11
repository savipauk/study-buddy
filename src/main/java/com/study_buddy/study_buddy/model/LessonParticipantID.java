package com.study_buddy.study_buddy.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class LessonParticipantID implements Serializable {
    
    @Column(name = "lesson_id", nullable = false)
    private Long lessonId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    public LessonParticipantID() {}

    public LessonParticipantID(Long userId, Long lessonId) {
        this.userId = userId;
        this.lessonId = lessonId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getLessonId() {
        return lessonId;
    }

    public void setLessonId(Long lessonId) {
        this.lessonId = lessonId;
    }

}
