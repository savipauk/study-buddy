package com.study_buddy.study_buddy.dto;

// Retrieve user's profile data by passing minimal identifying information

public class ProfileGet {

    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}