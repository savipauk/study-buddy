package com.study_buddy.study_buddy.dto;

public class ProfileGet {
    private static String email;

    public static String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}