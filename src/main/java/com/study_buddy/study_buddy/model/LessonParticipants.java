package com.study_buddy.study_buddy.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class LessonParticipants {
    @EmbeddedId
    private LessonParticipantID id;

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

    public LessonParticipantID getId() {
        return id;
    }
    public void setId(LessonParticipantID id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }

    public Lesson getLesson() {
        return lesson;
    }
    public void setLesson(Lesson lesson) {
        this.lesson = lesson;
    }

    public LocalDateTime getParticipationDate() {
        return participationDate;
    }
    public void setParticipationDate(LocalDateTime participationDate) {
        this.participationDate = participationDate;
    }
}
