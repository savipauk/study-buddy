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

    @PostMapping("/oauth")
    public Map<String, String> oauth(GoogleTokenResponse tokenResponse) {
        // GoogleTokenResponse is not naturally mapped from HTTP request bodies by
        // Spring Boot!!!
        // TODO: check if token needs to be handled as a JSON string or manually parsed
        // within OAuthService
        try {
            // verifying the token
            // parsing user information from the Google token payload
            // creating or updating the User in the database
            User user = oAuthService.processGoogleTokenResponse(tokenResponse);
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
        Map<String, String> returnData = Map.of("firstName", firstName, "lastName", lastName, "email", email,
                "hashedPassword", hashedPassword.toString(), "studyRole", studyRole.toString());

        // TODO: Save this user to the database
       /* User user = new User(email, hashedPassword, firstName, lastName, studyRole);

        userRepository.createUser(user);
*/
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
