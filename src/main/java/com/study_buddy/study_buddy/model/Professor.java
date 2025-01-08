package com.study_buddy.study_buddy.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "Professors")
public class Professor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "professor_id")
    private Long professorId;

    // CONNECTING TABLES USER-PROFESSOR
    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true) // References the user_id column in the Users table
    private User user;

    // CONNECTING TABLES PROFESSOR-LESSON
    // Professor is owner of Lesson
    @ManyToMany(mappedBy = "studentParticipants")
    private List<Lesson> lessons;

    // Constructors
    public Professor () {}

    public Professor(Long professorId, User user) {
        this.professorId = professorId;
        this.user = user;
    }

    // Getters and setters
    public Long getProfessorId() { return professorId; }

    public void setProfessorId(Long professorId) { this.professorId = professorId; }

    public User getUser() { return user; }

    public void setUser(User user) { this.user = user; }

    public List<Lesson> getLessons() { return lessons; }

    public void setLessons(List<Lesson> lessons) { this.lessons = lessons; }
}
