package com.study_buddy.study_buddy.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "Lessons")
public class Lesson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lesson_id")
    private Long lessonId;

    @Column(name = "subject")
    private String subject;

    @Column(name = "duration", nullable = false)
    private String duration;

    @Column(name = "max_participants", nullable = false)
    private int maxMembers;

    @Column(name = "min_participants", nullable = false)
    private int minMembers;

    @Column(name = "x_coordinate")
    private String xCoordinate;

    @Column(name = "y_coordinate")
    private String yCoordinate;

    @Column(name = "location")
    private String location;

    @Enumerated(EnumType.STRING)
    @Column(name = "lesson_type", nullable = false)
    private LessonType lessonType;

    @Column(name = "price")
    private Float price;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "time", nullable = false)
    private LocalTime time;

    @Column(name = "registration_deadline", nullable = false)
    private LocalDate registrationDeadline;

    // CONNECTING TABLES PROFESSOR-LESSON
    @ManyToOne
    @JoinColumn(name = "professor_id", nullable = false)
    private Professor professor;

    // CONNECTING TABLES STUDENT-LESSONPARTICIPANT
    @ManyToMany
    @JoinTable(
            name = "LessonParticipants",
            joinColumns = @JoinColumn(name = "lesson_id"),
            inverseJoinColumns = @JoinColumn(name = "participant_id")
    )
    private List<Professor> studentParticipants;


    // Constructors
    public Lesson() {
    }



    // Getters and setters
    public Long getLessonId() { return lessonId; }

    public void setLessonId(Long lessonId) { this.lessonId = lessonId; }

    public String getSubject() { return subject; }

    public void setSubject(String subject) { this.subject = subject; }

    public String getDuration() { return duration; }

    public void setDuration(String duration) { this.duration = duration; }

    public int getMaxMembers() { return maxMembers; }

    public void setMaxMembers(int maxMembers) { this.maxMembers = maxMembers; }

    public int getMinMembers() { return minMembers; }

    public void setMinMembers(int minMembers) { this.minMembers = minMembers; }

    public String getxCoordinate() { return xCoordinate; }

    public void setxCoordinate(String xCoordinate) { this.xCoordinate = xCoordinate; }

    public String getyCoordinate() { return yCoordinate; }

    public void setyCoordinate(String yCoordinate) { this.yCoordinate = yCoordinate; }

    public String getLocation() { return location; }

    public void setLocation(String location) { this.location = location; }

    public LessonType getLessonType() { return lessonType; }

    public void setLessonType(LessonType lessonType) { this.lessonType = lessonType; }

    public Float getPrice() { return price; }

    public void setPrice(Float price) { this.price = price; }

    public LocalDate getDate() { return date; }

    public void setDate(LocalDate date) { this.date = date; }

    public LocalTime getTime() { return time; }

    public void setTime(LocalTime time) { this.time = time; }

    public LocalDate getRegistrationDeadline() { return registrationDeadline; }

    public void setRegistrationDeadline(LocalDate registrationDeadline) { this.registrationDeadline = registrationDeadline; }

    public Professor getProfessor() { return professor; }

    public void setProfessor(Professor professor) { this.professor = professor; }

    @Override
    public String toString() {
        return "Lesson{" +
                "lessonId=" + lessonId +
                ", subject='" + subject + '\'' +
                ", duration='" + duration + '\'' +
                ", maxMembers=" + maxMembers +
                ", minMembers=" + minMembers +
                ", xCoordinate='" + xCoordinate + '\'' +
                ", yCoordinate='" + yCoordinate + '\'' +
                ", location='" + location + '\'' +
                ", lessonType=" + lessonType +
                ", price=" + price +
                ", date=" + date +
                ", time=" + time +
                ", registrationDeadline=" + registrationDeadline +
                ", professor=" + professor +
                ", studentParticipants=" + studentParticipants +
                '}';
    }
}
