package com.study_buddy.study_buddy.model;

import java.time.LocalDate;

import jakarta.persistence.*;

@Entity
@Table(name = "Lessons")
public class Lesson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lesson_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "professor_id", nullable = false)
    private Professor professor;

    @Enumerated(EnumType.STRING)
    @Column(name = "lesson_type", nullable = false)
    private LessonType lessonType;

    @Column(name = "location")
    private String location;

    @Column(name = "price")
    private Float price;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "duration", nullable = false)
    private int duration;

    @Column(name = "max_participants", nullable = false)
    private int maxParticipants;

    @Column(name = "min_participants", nullable = false)
    private int minParticipants;

    @Column(name = "registration_deadline", nullable = false)
    @Temporal(TemporalType.DATE)
    private LocalDate registrationDeadline;

    public enum LessonType {
        MASS("Mass"),
        ONE_ON_ONE("One on one");

        private final String value;

        LessonType(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    public Lesson() {
    }

    public Lesson(Long id, Professor professor, LessonType lessonType, String location, Float price,
            LocalDate date, int duration,
            int maxParticipants, int minParticipants, LocalDate registrationDeadline) {
        this.id = id;
        this.professor = professor;
        this.lessonType = lessonType;
        this.location = location;
        this.price = price;
        this.date = date;
        this.duration = duration;
        this.maxParticipants = maxParticipants;
        this.minParticipants = minParticipants;
        this.registrationDeadline = registrationDeadline;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Professor getProfessor() {
        return professor;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }

    public LessonType getLessonType() {
        return lessonType;
    }

    public void setLessonType(LessonType lessonType) {
        this.lessonType = lessonType;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getMaxParticipants() {
        return maxParticipants;
    }

    public void setMaxParticipants(int maxParticipants) {
        this.maxParticipants = maxParticipants;
    }

    public int getMinParticipants() {
        return minParticipants;
    }

    public void setMinParticipants(int minParticipants) {
        this.minParticipants = minParticipants;
    }

    public LocalDate getRegistrationDeadline() {
        return registrationDeadline;
    }

    public void setRegistrationDeadline(LocalDate registrationDeadline) {
        this.registrationDeadline = registrationDeadline;
    }
}
