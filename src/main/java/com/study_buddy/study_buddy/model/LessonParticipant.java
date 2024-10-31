package com.study_buddy.study_buddy.model;

import jakarta.persistence.*;

@Entity
@Table(name = "LessonParticipants")
public class LessonParticipant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lesson_id")
    private Long id;

    @ManyToOne
    @MapsId("lesson_id")
    @JoinColumn(name = "lesson_id")
    private Lesson lesson;

    @ManyToOne
    @MapsId("participant_id")
    @JoinColumn(name = "participant_id")
    private Student participant;

    public LessonParticipant () {}

    public LessonParticipant(Long id, Lesson lesson, Student participant) {
        this.id = id;
        this.lesson = lesson;
        this.participant = participant;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
