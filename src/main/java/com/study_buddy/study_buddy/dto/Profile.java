package com.study_buddy.study_buddy.dto;

import com.study_buddy.study_buddy.model.Gender;
import com.study_buddy.study_buddy.model.StudyRole;

import java.time.LocalDate;
import java.util.Map;

public class Profile {
    // private Long userId;
    private String email;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private StudyRole role;
    private String description;
    // private String accessToken;
    private String profilePicture;
    private LocalDate dateOfBirth;
    private String city;
    private Gender gender;

    public Map<String,String> profileResponse (String message){
        Map<String, String> response;
        if(password==null){ password = "DummyPass"; };
        if(gender!=null){
            response = Map.ofEntries(
                    Map.entry("firstName", this.getFirstName()),
                    Map.entry("lastName", this.getLastName()),
                    Map.entry("email", this.getEmail()),
                    Map.entry("studyRole", this.getRole().toString()),
                    Map.entry("username", this.getUsername()),
                    Map.entry("description", this.getDescription()),
                    Map.entry("password", this.getPassword()),
                    Map.entry("gender", this.getGender().toString()),
                    Map.entry("city", this.getCity()),
                    Map.entry("dateOfBirth", this.getDateOfBirth().toString()),
                    Map.entry("message", message)
            );
        } else {
            response = Map.ofEntries(
                    Map.entry("firstName", this.getFirstName()),
                    Map.entry("lastName", this.getLastName()),
                    Map.entry("email", this.getEmail()),
                    Map.entry("studyRole", this.getRole().toString()),
                    Map.entry("username", this.getUsername()),
                    Map.entry("description", this.getDescription()),
                    Map.entry("password", this.getPassword()),
                    Map.entry("message", message),
                    Map.entry("gender", ""),
                    Map.entry("city", ""),
                    Map.entry("dateOfBirth","")
            );
        }

        return response;
    };

    public StudyRole getRole() {
        return role;
    }

    public void setRole(StudyRole role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDateOfBirth() { return dateOfBirth; }

    public void setDateOfBirth(LocalDate dateOfBirth) { this.dateOfBirth = dateOfBirth; }

    public String getCity() { return city; }

    public void setCity(String city) { this.city = city; }

    public Gender getGender() { return gender; }

    public void setGender(Gender gender) { this.gender = gender; }

    public String getUsername() { return username; }

    public void setUsername(String Username) { this.username = Username; }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String getPassword() { return password; }

    public void setPassword(String password) { this.password = password; }
}