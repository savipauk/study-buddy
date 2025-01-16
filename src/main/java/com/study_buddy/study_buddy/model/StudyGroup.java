package com.study_buddy.study_buddy.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Entity
@Table(name = "StudyGroups")
public class StudyGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_id")
    private Long groupId;

    @Column(name = "group_name", nullable = false, length = 255)
    private String groupName;

    @Column(name = "location")
    private String location;

    @Column(name = "x_coordinate")
    private String xCoordinate;

    @Column(name = "y_coordinate")
    private String yCoordinate;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "time")
    private LocalTime time;

    @Column(name = "max_members", nullable = false)
    private int maxMembers;

    @Column(name = "description", length = 255)
    private String description;

    @Column(name = "expiration_date", nullable = false)
    private LocalDate expirationDate;

    // CONNECTING TABLES STUDENT-STUDYGROUP
    @ManyToOne
    @JoinColumn(name = "creator_id", nullable = false)
    private Student creator;

    // CONNECTING TABLES STUDENT-STUDYGROUP -> GROUPMEMBERS
    @ManyToMany
    @JoinTable(
            name = "GroupMembers",
            joinColumns = @JoinColumn(name = "group_id"),
            inverseJoinColumns = @JoinColumn(name = "member_id")
    )
    private List<Student> creators;

    public StudyGroup() {
    }

    public Map<String,String> studyGroupResponse(String message){
        Map<String,String> response = Map.of(
                //"creator", this.creator.get,
                "groupName", this.getGroupName(),
                "xCoortinate", this.getxCoordinate(),
                "yCoordinate", this.getyCoordinate(),
                "date", this.getDate().toString(),
                "maxMembers", String.valueOf(this.getMaxMembers()),
                "description", this.getDescription(),
                "message", message
        );
        return response;
    }


    public StudyGroup(Long id, Student creator, String groupName, String location, LocalDate date, int maxMembers,
            String description, LocalDate expirationDate) {
        this.groupId = id;
        this.creator = creator;
        this.groupName = groupName;
        this.date = date;
        this.maxMembers = maxMembers;
        this.description = description;
        this.expirationDate = expirationDate;
    }

    public Long getGroupId() { return groupId; }

    public void setGroupId(Long groupId) { this.groupId = groupId; }

    public Student getCreator() { return creator; }

    public void setCreator(Student creator) { this.creator = creator; }

    public String getGroupName() { return groupName; }

    public void setGroupName(String groupName) { this.groupName = groupName; }

    public LocalDate getDate() { return date; }

    public void setDate(LocalDate date) { this.date = date; }

    public int getMaxMembers() { return maxMembers; }

    public void setMaxMembers(int maxMembers) { this.maxMembers = maxMembers; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public String getxCoordinate() { return xCoordinate; }

    public void setxCoordinate(String xCoordinate) { this.xCoordinate = xCoordinate; }

    public String getyCoordinate() { return yCoordinate; }

    public void setyCoordinate(String yCoordinate) { this.yCoordinate = yCoordinate; }

    public String getLocation() { return location; }

    public void setLocation(String location) { this.location = location; }

    public LocalTime getTime() { return time; }

    public void setTime(LocalTime time) { this.time = time; }

    public LocalDate getExpirationDate() { return expirationDate; }

    public void setExpirationDate(LocalDate expirationDate) { this.expirationDate = expirationDate; }

    @Override
    public String toString() {
        return "StudyGroup{" +
                "groupId=" + groupId +
                ", groupName='" + groupName + '\'' +
                ", location='" + location + '\'' +
                ", xCoordinate='" + xCoordinate + '\'' +
                ", yCoordinate='" + yCoordinate + '\'' +
                ", date=" + date +
                ", time=" + time +
                ", maxMembers=" + maxMembers +
                ", description='" + description + '\'' +
                ", expirationDate=" + expirationDate +
                ", creator=" + creator +
                ", creators=" + creators +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StudyGroup sg = (StudyGroup) o;
        return Objects.equals(groupId, sg.getGroupId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(groupId);
    }
}
