package com.study_buddy.study_buddy.dto;

import com.study_buddy.study_buddy.model.LessonType;

import java.time.LocalDate;
import java.time.LocalTime;


public class LessonDto {
    private Long lessonId;
    private String email;    // Email of professor
    private String subject;
    private String duration;
    private int maxMembers;
    private int minMembers;
    private String xCoordinate;
    private String yCoordinate;
    private String location;
    private LessonType type;
    private Float price;
    private LocalDate date;
    private LocalTime time;
    private String username;

    //private String description;
    private LocalDate registrationDeadLine;

    // Constructor
    public LessonDto(){};

    // Getters and setters

    public Long getLessonId() { return lessonId; }

    public void setLessonId(Long lessonId) { this.lessonId = lessonId; }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

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

    public LessonType getType() { return type; }

    public void setType(LessonType type) { this.type = type; }

    public Float getPrice() { return price; }

    public void setPrice(Float price) { this.price = price; }

    public LocalDate getDate() { return date; }

    public void setDate(LocalDate date) { this.date = date; }

    public LocalTime getTime() { return time; }

    public void setTime(LocalTime time) { this.time = time; }

    public LocalDate getRegistrationDeadLine() { return registrationDeadLine; }

    public void setRegistrationDeadLine(LocalDate registrationDeadLine) { this.registrationDeadLine = registrationDeadLine; }

    public String getUsername() { return username; }

    public void setUsername(String username) { this.username = username; }

    @Override
    public String toString() {
        return "LessonDto{" +
                "lessonId=" + lessonId +
                ", email='" + email + '\'' +
                ", subject='" + subject + '\'' +
                ", duration='" + duration + '\'' +
                ", maxMembers=" + maxMembers +
                ", minMembers=" + minMembers +
                ", xCoordinate='" + xCoordinate + '\'' +
                ", yCoordinate='" + yCoordinate + '\'' +
                ", location='" + location + '\'' +
                ", type=" + type +
                ", price=" + price +
                ", date=" + date +
                ", time=" + time +
                ", registrationDeadLine=" + registrationDeadLine +
                '}';
    }
}
