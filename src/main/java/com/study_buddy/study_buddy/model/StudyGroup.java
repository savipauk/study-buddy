package com.study_buddy.study_buddy.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Entity
@Table(name = "StudyGroups")
public class StudyGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "study_group_id")
    private Long studyGroupId;

    // TODO: CHANGE USER TO STUDENT
    @ManyToOne
    @JoinColumn(name = "creator_id", nullable = false)
    private User creator;

    @Column(name = "group_name", nullable = false, length = 255)
    private String groupName;

    @Column(name = "location")
    private String location;

    @Column(name = "x-coordinate")
    private String xCoordinate;

    @Column(name = "y-coordinate")
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

    @ManyToMany
    @JoinTable(
            name = "StudyGroupParticipants",
            joinColumns = @JoinColumn(name = "study_group_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id")
    )
    private List<Student> creators;
    //private Set<Student> creators = new HashSet<>();

    public StudyGroup() {
    }

    /*public void addParticipant(User student) {
        if (participants.size() < maxMembers) {
            participants.add(student);
            student.getStudyGroups().add(this);
        } else {
            throw new IllegalStateException("Study group is full!");
        }
    }

    public void removeParticipant(Student student) {
        participants.remove(student);
        student.getStudyGroups().remove(this);
    }*/

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


    public StudyGroup(Long id, User creator, String groupName, String location, LocalDate date, int maxMembers,
            String description, LocalDate expirationDate) {
        this.studyGroupId = id;
        this.creator = creator;
        this.groupName = groupName;
        this.date = date;
        this.maxMembers = maxMembers;
        this.description = description;
        this.expirationDate = expirationDate;
    }

    public Long getId() { return studyGroupId; }

    public void setId(Long id) { this.studyGroupId = id; }

    public User getCreator() { return creator; }

    public void setCreator(User creator) { this.creator = creator; }

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
}
