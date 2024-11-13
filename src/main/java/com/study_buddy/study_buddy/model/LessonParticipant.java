package com.study_buddy.study_buddy.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "LessonParticipants")
public class LessonParticipant {

    @EmbeddedId
    private LessonParticipantID id;

    @MapsId("studentId")
    @ManyToOne
    @JoinColumn(name = "participant_id", nullable = false)
    private Student participantId;

    @MapsId("lessonId")
    @ManyToOne
    @JoinColumn(name = "lesson_id", nullable = false)
    private Lesson lessonId;

    @Column(name = "participation_date", nullable = false)
    private LocalDateTime participationDate;

    public LessonParticipant() {}

    public LessonParticipant(Student participantId, Lesson lessonId, LocalDateTime participationDate) {
        this.participantId = participantId;
        this.lessonId = lessonId;
        this.participationDate = participationDate;
    }


    public LessonParticipantID getId() {
        return id;
    }

    public void setId(LessonParticipantID id) {
        this.id = id;
    }

    public Student getParticipant() {
        return participantId;
    }

    public void setParticipant(Student participantId) {
        this.participantId = participantId;
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
