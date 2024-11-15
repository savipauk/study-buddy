package com.study_buddy.study_buddy.dto;

import com.study_buddy.study_buddy.model.StudyRole;

// Send profile data back to the frontend, includes only fields relevant to displaying the user profile

public class ProfileResponse {

    private String email;
    private String firstName;
    private String lastName;
    private StudyRole role;
    private String description;
    private String profilePicture;

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

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }
}