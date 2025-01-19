package com.study_buddy.study_buddy.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
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
    // Student is perticipant of studyGroup
    @ManyToMany(mappedBy = "participants", cascade = {CascadeType.MERGE, CascadeType.REMOVE})
    private List<StudyGroup> studyGroups;

    // CONNECTING TABLES STUDENT-LESSON
    // Student is participang of lesson
    @ManyToMany(mappedBy = "studentParticipants", cascade = {CascadeType.MERGE, CascadeType.REMOVE})
    private List<Lesson> lessons;


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

    public List<StudyGroup> getStudyGroups() { return studyGroups;}

    public void setStudyGroups(List<StudyGroup> studyGroups) { this.studyGroups = studyGroups; }

    public List<Lesson> getLessons() { return lessons; }

    public void setLessons(List<Lesson> lessons) { this.lessons = lessons; }

    @Override
    public String toString() {
        return "Student{" +
                "studentId=" + studentId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return Objects.equals(studentId, student.studentId) && Objects.equals(user, student.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentId, user);
    }
}
