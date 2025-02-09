package com.study_buddy.study_buddy.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

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



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Professor professor = (Professor) o;
        return Objects.equals(professorId, professor.professorId) && Objects.equals(user, professor.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(professorId, user);
    }
}
