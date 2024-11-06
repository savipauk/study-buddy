package com.study_buddy.study_buddy.model;

import jakarta.persistence.*;

@Entity
@Table(name = "LessonParticipants")
public class LessonParticipant {

    @EmbeddedId
    private LessonParticipantId id = new LessonParticipantId();

    @MapsId("lessonId")
    @JoinColumn(name = "lesson_id")
    private Lesson lesson;

    @ManyToOne
    @MapsId("participantId")
    @JoinColumn(name = "participant_id")
    private Student participant;

    public LessonParticipant() {
    }

    public LessonParticipant(LessonParticipantId id, Lesson lesson, Student participant) {
        this.id = id;
        this.lesson = lesson;
        this.participant = participant;
    }

    public LessonParticipantId getId() {
        return id;
    }

    public void setId(LessonParticipantId id) {
        this.id = id;
    }

    public Lesson getLesson() {
        return lesson;
    }

    public void setLesson(Lesson lesson) {
        this.lesson = lesson;
    }

    public Student getParticipant() {
        return participant;
    }

    public void setParticipant(Student participant) {
        this.participant = participant;
    }
}
