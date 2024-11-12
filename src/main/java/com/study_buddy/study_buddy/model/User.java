package com.study_buddy.study_buddy.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "email", nullable = false, unique = true, length = 100)
    private String email;

    @Column(name="password", length = 255)
    private String password;

    @Column(name = "oauth_provider", length = 50)
    private String oauthProvider;

    @Column(name = "oauth_id", unique = true, length = 255)
    private String oauthId;

    @Column(name = "first_name", nullable = false, length = 100)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 100)
    private String lastName;

    @Column(name = "access_token", length = 255)
    private String access_Token;

    @Column(name = "refresh_token", length = 255)
    private String refresh_Token;


    @Column(name = "role", nullable = false)
    private StudyRole role;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    public User () {}

    public User(String email, String password, String oauthProvider, String oauthId, String firstName, String lastName, StudyRole role) {
        this.email = email;
        this.password = password;
        this.oauthProvider = oauthProvider;
        this.oauthId = oauthId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.access_Token = access_Token;
        this.refresh_Token = refresh_Token;
        this.role = role;
    }

    public User(String email, String password, String firstName, String lastName, StudyRole role) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public User(String accessToken, LocalDateTime createdAt, String email, String firstName, String lastName, String oauthProvider, String oauthId, String hashedPassword, String refreshed_Token, StudyRole studyRole, LocalDateTime updatedAt) {
        this.access_Token = accessToken;
        this.createdAt = createdAt;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.oauthProvider = oauthProvider;
        this.oauthId = oauthId;
        this.password = hashedPassword;
        this.refresh_Token = refreshed_Token;
        this.role = studyRole;
        this.updatedAt = updatedAt;
    }

    public Long getUserId() {
        return id;
    }

    public void setUserId(Long userId) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getOauthProvider() {
        return oauthProvider;
    }

    public void setOauthProvider(String oauthProvider) {
        this.oauthProvider = oauthProvider;
    }

    public String getOauthId() {
        return oauthId;
    }

    public void setOauthId(String oauthId) {
        this.oauthId = oauthId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setAccess_Token(String access_Token) { this.access_Token = access_Token; }

    public String getAccess_Token() { return access_Token; }

    public void setRefresh_Token(String refresh_Token) { this.refresh_Token = refresh_Token; }

    public String getRefresh_Token() { return refresh_Token; }

    public StudyRole getRole() {
        return role;
    }

    public void setRole(StudyRole role) {
        this.role = role;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }


    /*public enum Role {
        STUDENT, PROFESSOR, ADMIN
    }*/
}
