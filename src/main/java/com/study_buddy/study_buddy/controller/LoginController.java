package com.study_buddy.study_buddy.controller;

import org.springframework.web.bind.annotation.*;

import com.study_buddy.study_buddy.dto.Login;
import com.study_buddy.study_buddy.dto.Register;
import com.study_buddy.study_buddy.dto.StudyRole;

import java.util.Map;

@RestController
@RequestMapping("/login")
public class LoginController {

    @PostMapping("/oauth")
    public Map<String, String> oauth() {
        // TODO: Save: user Google id, email address, name, profile picture url,
        // access token / refresh token, login method = google, created at
        // This data can be retrieved from the google access token

        return Map.of("test", "test");
    }

    @PostMapping(value = "/register", produces = "application/json")
    public Map<String, String> register(@RequestBody Register data) {
        String email = data.getEmail();
        String firstName = data.getFirstName();
        String lastName = data.getLastName();
        String hashedPassword = data.getHashedPassword();
        StudyRole studyRole = data.getStudyRole();
        Map<String, String> returnData = Map.of("firstName", firstName, "lastName", lastName, "email", email,
                "hashedPassword", hashedPassword, "studyRole", studyRole.toString());

        // TODO: Save this user to the database

        return returnData;
    }

    @PostMapping(value = "/login", produces = "application/json")
    public Map<String, String> login(@RequestBody Login data) {
        String email = data.getEmail();
        String hashedPassword = data.getHashedPassword();

        // TODO: Check in database if user exists and send a response

        return Map.of("email", email, "hashedPassword", hashedPassword);
    }
}
