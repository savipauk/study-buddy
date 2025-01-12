package com.study_buddy.study_buddy.model;

import com.study_buddy.study_buddy.service.StudentService;
import jakarta.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.spel.ast.NullLiteral;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "oauth_provider", length = 50)
    private String oauthProvider;

    @Column(name = "oauth_id", length = 255)
    private String oauthId;

    @Column(name = "first_name", nullable = false, length = 100)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 100)
    private String lastName;

    @Column(name = "description", length = 255)
    private String description;

    @Column(name = "access_token", length = 255)
    private String access_Token;

    @Column(name = "refresh_token", length = 255)
    private String refresh_Token;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "role", nullable = false)
    private StudyRole role;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Column(name = "profile_picture", length = 255)
    private String profilePicture;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private Gender gender;

    @Column(name = "city", length = 100)
    private String city;

    // CONNECTING TABLES USERS-STUDENTS
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Student student;

    // CONNECTING TABLES USERS-PROFESSORS
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Professor professor;

    // CONNECTING TABLES USERS-REPORTS (user is the reporter)
    @OneToMany(mappedBy = "reporter", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Report> reportsSubmitted = new ArrayList<>();

    // CONNECTING TABLES USERS-REPORTS (user is the reported user)
    @OneToMany(mappedBy = "reportedUser", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Report> reportsReceived = new ArrayList<>();



    public User () {}

    // Constructor for Oauth
    public User(String email, String username, String oauthProvider, String oauthId, String firstName, String lastName, StudyRole role) {
        this.email = email;
        this.oauthProvider = oauthProvider;
        this.oauthId = oauthId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.description = "";
        this.username = username;
        this.role = role;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.access_Token = "";
        this.refresh_Token = "";
    }


    public User(String email, String password, String firstName, String lastName, StudyRole role) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.description = "";
        this.role = role;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    // Constructor for log/reg without OAuth
    public User(String username, String accessToken, LocalDateTime createdAt, String email, String firstName, String lastName, String oauthProvider, String oauthId, String hashedPassword, String refreshed_Token, StudyRole studyRole, LocalDateTime updatedAt) {
        this.access_Token = accessToken;
        this.createdAt = createdAt;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.description = "";
        this.oauthProvider = oauthProvider;
        this.oauthId = oauthId;
        this.password = hashedPassword;
        this.refresh_Token = refreshed_Token;
        this.role = studyRole;
        this.updatedAt = updatedAt;
        this.username = username;
    }

    public Map<String,String> response(String message){
        Map<String, String> response;
        response = Map.of(
                "firstName", this.getFirstName(),
                "lastName", this.getLastName(),
                "email", this.getEmail(),
                "studyRole", this.getRole().toString(),
                "username", this.getUsername(),
                "description", this.getDescription(),
                "message", message
        );
        return response;
    }

    public Long getUserId() { return id; }

    public void setUserId(Long userId) { this.id = id; }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }

    public void setPassword(String password) { this.password = password; }

    public String getOauthProvider() { return oauthProvider; }

    public void setOauthProvider(String oauthProvider) { this.oauthProvider = oauthProvider; }

    public String getOauthId() { return oauthId; }

    public void setOauthId(String oauthId) { this.oauthId = oauthId; }

    public String getFirstName() { return firstName; }

    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }

    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public void setAccess_Token(String access_Token) { this.access_Token = access_Token; }

    public String getAccess_Token() { return access_Token; }

    public void setRefresh_Token(String refresh_Token) { this.refresh_Token = refresh_Token; }

    public String getRefresh_Token() { return refresh_Token; }

    public StudyRole getRole() { return role; }

    public void setRole(StudyRole role) { this.role = role; }

    public LocalDateTime getCreatedAt() { return createdAt; }

    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }

    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public String getUsername() { return username; }

    public void setUsername(String username) { this.username = username;}

    public String getProfilePicture() {return profilePicture; }

    public void setProfilePicture(String profilePicture) { this.profilePicture = profilePicture; }

    public LocalDate getDateOfBirth() { return dateOfBirth; }

    public void setDateOfBirth(LocalDate dateOfBirth) { this.dateOfBirth = dateOfBirth; }

    public Gender getGender() { return gender; }

    public void setGender(Gender gender) { this.gender = gender; }

    public String getCity() { return city; }

    public void setCity(String city) { this.city = city; }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", username='" + username + '\'' +
                ", oauthProvider='" + oauthProvider + '\'' +
                ", oauthId='" + oauthId + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", description='" + description + '\'' +
                ", access_Token='" + access_Token + '\'' +
                ", refresh_Token='" + refresh_Token + '\'' +
                ", role=" + role +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", profilePicture='" + profilePicture + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", gender=" + gender +
                ", city='" + city + '\'' +
                '}';
    }
}
