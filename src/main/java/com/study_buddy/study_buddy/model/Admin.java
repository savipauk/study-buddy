package com.study_buddy.study_buddy.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Admins")
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "admin_id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false, length = 100)
    private String username;

    @Column(length = 255)
    private String profilePicture;

    public Admin() {}

    public Admin(Long adminId, User user, String username, String profilePicture) {
        this.id = id;
        this.user = user;
        this.username = username;
        this.profilePicture = profilePicture;
    }

    public Long getAdminId() {
        return id;
    }

    public void setAdminId(Long adminId) {
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
}
