package com.study_buddy.study_buddy.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public class StudyGroupDto {
    private Long studyGroupId; // Optional for responses
    private String email;    // User ID of the creator
    private String groupName;
    private String location;
    private String xCoordinate;
    private String yCoordinate;
    private LocalDate date;
    private LocalTime time;
    private int maxMembers;
    private String description;
    private LocalDate expirationDate;

    // Constructors
    public StudyGroupDto() {}

    public StudyGroupDto(Long studyGroupId, String creatorEmail, String groupName, String location, String xCoordinate,
                         String yCoordinate, LocalDate date, LocalTime time, int maxMembers,
                         String description, LocalDate expirationDate) {
        this.studyGroupId = studyGroupId;
        this.email = creatorEmail;
        this.groupName = groupName;
        this.location = location;
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        this.date = date;
        this.time = time;
        this.maxMembers = maxMembers;
        this.description = description;
        this.expirationDate = expirationDate;
    }

    // Getters and Setters
    public Long getStudyGroupId() { return studyGroupId; }

    public void setStudyGroupId(Long studyGroupId) { this.studyGroupId = studyGroupId; }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public String getGroupName() { return groupName; }

    public void setGroupName(String groupName) { this.groupName = groupName; }

    public String getLocation() { return location; }

    public void setLocation(String location) { this.location = location; }

    public String getxCoordinate() { return xCoordinate; }

    public void setxCoordinate(String xCoordinate) { this.xCoordinate = xCoordinate; }

    public String getyCoordinate() { return yCoordinate; }

    public void setyCoordinate(String yCoordinate) { this.yCoordinate = yCoordinate; }

    public LocalDate getDate() { return date; }

    public void setDate(LocalDate date) { this.date = date; }

    public LocalTime getTime() { return time; }

    public void setTime(LocalTime time) { this.time = time; }

    public int getMaxMembers() { return maxMembers; }

    public void setMaxMembers(int maxMembers) { this.maxMembers = maxMembers; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public LocalDate getExpirationDate() { return expirationDate; }

    public void setExpirationDate(LocalDate expirationDate) { this.expirationDate = expirationDate; }

    @Override
    public String toString() {
        return "StudyGroupDto{" +
                "studyGroupId=" + studyGroupId +
                ", creatorId=" + email +
                ", groupName='" + groupName + '\'' +
                ", location='" + location + '\'' +
                ", xCoordinate='" + xCoordinate + '\'' +
                ", yCoordinate='" + yCoordinate + '\'' +
                ", date=" + date +
                ", time=" + time +
                ", maxMembers=" + maxMembers +
                ", description='" + description + '\'' +
                ", expirationDate=" + expirationDate +
                '}';
    }
}
