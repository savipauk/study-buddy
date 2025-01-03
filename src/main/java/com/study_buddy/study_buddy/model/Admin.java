package com.study_buddy.study_buddy.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Admins")
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long adminId;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false, length = 100)
    private String username;

    @Column(length = 255)
    private String profilePicture;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    public enum Gender {
        M, F, OTHER
    }

    public Admin() {}

    public Admin(Long adminId, User user, String username, Gender gender, String profilePicture) {
        this.adminId = adminId;
        this.user = user;
        this.username = username;
        this.gender = gender;
        this.profilePicture = profilePicture;
    }

    public Long getAdminId() {
        return adminId;
    }

    public void setAdminId(Long adminId) {
        this.adminId = adminId;
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

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }
}
