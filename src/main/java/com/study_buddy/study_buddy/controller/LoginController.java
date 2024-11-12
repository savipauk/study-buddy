package com.study_buddy.study_buddy.controller;

import java.util.Map;

import com.study_buddy.study_buddy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.study_buddy.study_buddy.dto.Login;
import com.study_buddy.study_buddy.dto.Register;
import com.study_buddy.study_buddy.model.StudyRole;
import com.study_buddy.study_buddy.model.User;
import com.study_buddy.study_buddy.service.OAuthService;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private OAuthService oAuthService;

    @Autowired
    private UserService userRepository;
    @Autowired
    private UserService userService;

    @PostMapping("/oauth")
    public Map<String, String> oauth(GoogleTokenResponse tokenResponse) {

        try {
            User user = oAuthService.processGoogleTokenResponse(tokenResponse);
            userService.saveOrUpdateUser(user); // NOVO
            return Map.of("status", "success", "message", "User " + user.getEmail() + " processed successfully");
        } catch (Exception e) {
            return Map.of("status", "error", "message", e.getMessage());
        }
    }

    @PostMapping(value = "/register", produces = "application/json")
    public Map<String, String> register(@RequestBody Register data) {
        String email = data.getEmail();
        String firstName = data.getFirstName();
        String lastName = data.getLastName();
        String hashedPassword = data.getHashedPassword();
        StudyRole studyRole = data.getStudyRole();

        if (userService.userExistsByEmail(email)) {
            return Map.of("status", "error", "message", "User already exists with email: " + email);
        }

        // Create a new User and save it to the database
        User user = new User(email, hashedPassword, firstName, lastName, studyRole);
        userService.createUser(user);

        return Map.of(
                "status", "success",
                "message", "User registered successfully",
                "firstName", firstName,
                "lastName", lastName,
                "email", email
        );
    }

    @PostMapping(value = "/login", produces = "application/json")
    public Map<String, String> login(@RequestBody Login data) {
        String email = data.getEmail();
        String hashedPassword = data.getHashedPassword();

        User user = userService.getUserByEmail(email);

        // TODO: after creating passwordEncoder
//        if (user != null && userService.verifyPassword(user, hashedPassword)) {
//            return Map.of("status", "success", "message", "Login successful");
//        } else {
//            return Map.of("status", "error", "message", "Invalid email or password");
//        }

        return Map.of("status", "success", "message", "Login successful");

    }
}
