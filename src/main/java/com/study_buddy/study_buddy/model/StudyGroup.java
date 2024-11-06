package com.study_buddy.study_buddy.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "StudyGroups")
public class StudyGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "creator_id", nullable = false)
    private Student creator;

    @Column(name = "group_name", nullable = false, length = 255)
    private String groupName;

    @Column(name = "location")
    private String location;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "max_members", nullable = false)
    private int maxMembers;

    @Column(name = "description", length = 255)
    private String description;

    @Column(name = "expiration_date", nullable = false)
    private LocalDate expirationDate;

    public StudyGroup() {
    }

    public StudyGroup(Long id, Student creator, String groupName, String location, LocalDate date, int maxMembers,
            String description, LocalDate expirationDate) {
        this.id = id;
        this.creator = creator;
        this.groupName = groupName;
        this.location = location;
        this.date = date;
        this.maxMembers = maxMembers;
        this.description = description;
        this.expirationDate = expirationDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Student getCreator() {
        return creator;
    }

    public void setCreator(Student creator) {
        this.creator = creator;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getMaxMembers() {
        return maxMembers;
    }

    public void setMaxMembers(int maxMembers) {
        this.maxMembers = maxMembers;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }
}
