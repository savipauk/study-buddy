package com.study_buddy.study_buddy.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "Students")
public class Student{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_id")
    private Long studentId;

    // CONNECTING TABLES USER-STUDENT
    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true) // References the user_id column in the Users table
    private User user;

    // CONNECTING TABLES STUDENT-STUDYGROUP
    // Student is creator of studyGroup
    @ManyToMany(mappedBy = "participants")
    private List<StudyGroup> studyGroups;


    // Constructors
    public Student() {}

    public Student(Long student_id, User user) {
        this.studentId = student_id;
        this.user = user;
    }

    // Getters and setters
    public User getUser() { return user; }

    public void setUser(User user) { this.user = user; }

    public Long getStudentId() { return studentId; }

    public void setStudentId(Long student_id) { this.studentId = student_id; }

    @Override
    public String toString() {
        return "Student{" +
                "studentId=" + studentId +
                '}';
    }
}
