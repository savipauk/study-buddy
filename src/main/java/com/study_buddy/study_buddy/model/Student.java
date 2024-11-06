package com.study_buddy.study_buddy.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "Students")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "username", nullable = false, length = 100)
    private String username;

    @Column(name = "profile_picture", length = 255)
    private String profilePicture;

    @Temporal(TemporalType.DATE)
    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;


    @Column(name = "profile_picture", length = 100)
    private String city;

    @Column(name = "description", length = 255)
    private String description;

    // public enum Gender {
    // M, F, OTHER
    // }

    public Student() {
    }

    public Student(Long id, User user, String username, String profilePicture, LocalDate dateOfBirth, 
            String city, String description) {
        this.id = id;
        this.user = user;
        this.username = username;
        this.profilePicture = profilePicture;
        this.dateOfBirth = dateOfBirth;
        this.city = city;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }


    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
