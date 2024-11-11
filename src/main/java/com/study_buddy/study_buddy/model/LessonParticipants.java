package com.study_buddy.study_buddy.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "LessonParticipants")
public class LessonParticipants {
    @EmbeddedId
    private LessonParticipantID id;

    @MapsId("userId")
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User userId;

    @MapsId("lessonId")
    @ManyToOne
    @JoinColumn(name = "lesson_id", nullable = false)
    private Lesson lessonId;

    @Column(name = "participation_date", nullable = false)
    private LocalDateTime participationDate;

    public LessonParticipantID getId() {
        return id;
    }

    public void setId(LessonParticipantID id) {
        this.id = id;
    }

    public User getUser() {
        return userId;
    }

    public void setUser(User userId) {
        this.userId = userId;
    }

    public Lesson getLesson() {
        return lessonId;
    }
    public void setLesson(Lesson lessonId) {
        this.lessonId = lessonId;
    }

    public LocalDateTime getParticipationDate() {
        return participationDate;
    }

    public void setParticipationDate(LocalDateTime participationDate) {
        this.participationDate = participationDate;
    }
}
