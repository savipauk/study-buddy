package com.study_buddy.study_buddy.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Students")
public class Student{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_id")
    private Long studentId;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true) // References the user_id column in the Users table
    private User user;

    /*@OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Long user_id;*/


   /* @ManyToMany(mappedBy = "participants")
    private Set<StudyGroup> studyGroups = new HashSet<>();*/

    public Student() {}

    public Student(Long student_id, User user) {
        this.studentId = student_id;
        this.user = user;
    }


    public User getUser() { return user; }

    public void setUser(User user) { this.user = user; }

    public Long getStudent_id() { return studentId; }

    public void setStudent_id(Long student_id) { this.studentId = student_id; }

}
