package com.study_buddy.study_buddy.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import com.study_buddy.study_buddy.model.StudyRole;
import com.study_buddy.study_buddy.service.JwtService;
import com.study_buddy.study_buddy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.study_buddy.study_buddy.dto.Login;
import com.study_buddy.study_buddy.dto.Register;
import com.study_buddy.study_buddy.model.User;
import com.study_buddy.study_buddy.service.OAuthService;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private OAuthService oAuthService;

    @Autowired
    private UserService userService;

    @PostMapping("/oauth")
    public Map<String, String> oauth(@RequestBody Map<String, String> requestBody) {
        String credential = requestBody.get("credential");

        if (credential == null) {
            Map<String, String> response = Map.of("status", "error", "message", "Credential is missing");
            return ResponseEntity.ok()
                    //.header(HttpHeaders.AUTHORIZATION, "Bearer " + user.getAccess_Token())
                    .body(response).getBody();
        }

        Map<String, String> response;
        String registration;

        try {
            User user = oAuthService.processGoogleTokenResponse(credential);
            if (userService.userExistsByEmail(user.getEmail())) {
                registration = "LOGIN_OAUTH_OK";

            } else {
                registration = "REGISTRATION_OAUTH_OK";
                userService.createUser(user);
            }

            response = Map.of(
                    "firstName", user.getFirstName(),
                    "lastName", user.getLastName(),
                    "email", user.getEmail(),
                    "studyRole", user.getRole().getValue(),
                    "token", user.getAccess_Token(),
                    "username", user.getUsername(),
                    "message", "Registration successful",
                    "registration", registration
            );


            return ResponseEntity.ok()
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + user.getAccess_Token())
                    .body(response).getBody();
        } catch (Exception e) {
            response = Map.of("status", "error", "message", e.getMessage());
            return ResponseEntity.ok()
                    //.header(HttpHeaders.AUTHORIZATION, "Bearer " + user.getAccess_Token())
                    .body(response).getBody();
        }
    }


    @PostMapping(value = "/register", produces = "application/json")
    public Map<String, String> register(@RequestBody Register data) {
        String email = data.getEmail();
        String firstName = data.getFirstName();
        String lastName = data.getLastName();
        String hashedPassword = data.getHashedPassword();
        StudyRole studyRole = data.getStudyRole();
        String username = data.getUsername();

        JwtService jwtService = new JwtService();
        String token = jwtService.generateToken(data.getEmail(), data.getUsername());

        // Check is this username already exists
        if (userService.userExistsByUsername(username)) {
            return ResponseEntity.ok()
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                    .body(data.responseRegister("USERNAME_EXISTS")).getBody();
        }

        // Check if this email already exists
        if (userService.userExistsByEmail(email)) {
            //Map<String, String> response = Map.of("email", email, "username", username, "message", "User with this email already exists!", "registration", "EMAIL_EXISTS");
            return ResponseEntity.ok()
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                    .body(data.responseRegister("EMAIL_EXISTS")).getBody();
        }

        // Save this user to the database
        User user = new User(username, token, LocalDateTime.now(), email, firstName, lastName, "", "", hashedPassword, "", studyRole, LocalDateTime.now());
        userService.createUser(user);

        // Create Authentication object
        List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_USER"));  // You can adjust the authorities as needed
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                user.getEmail(), null, authorities);

        // Set the authentication in the SecurityContext
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);

        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .body(data.responseRegister("REGISTRATION_OK")).getBody();
    }

    @PostMapping(value = "/login", produces = "application/json")
    public Map<String, String> login(@RequestBody Login data) {
        String username = data.getUsername();
        String rawPassword = data.getHashedPassword().toString();

        Map<String, String> response;

        if (!userService.userExistsByUsername(username)) {
            response = Map.of("username", username,"passwordCheck", "DOESNT_EXIST", "message", "User doesn't exist");
            return ResponseEntity.ok()
                    .body(response).getBody();
        }


        // Username is unique
        User user = userService.getUserByUsername(username);
        if (!userService.verifyPassword(user, rawPassword)) {
            response = Map.of("username", username,"email", user.getEmail(),"passwordCheck", "NOT_OK", "message", "Wrong password");

        } else {
            response = Map.of("username", username, "email",user.getEmail(),"passwordCheck", "OK", "message", "Successful login");
        }
        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + user.getAccess_Token())
                .body(response).getBody();
    }
}
