package com.study_buddy.study_buddy.controller;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.study_buddy.study_buddy.model.StudyRole;
import com.study_buddy.study_buddy.service.JwtService;
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
import org.springframework.http.converter.HttpMessageNotReadableException;

import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
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
    private UserService userRepository;

    @PostMapping("/oauth")
    public Map<String, String> oauth(@RequestBody Map<String, String> requestBody) {
        String credential = requestBody.get("credential");

        if (credential == null) {
            return Map.of("status", "error", "message", "Credential is missing");
        }


        try {
            User user = oAuthService.processGoogleTokenResponse(credential);
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
        String username = data.getUsername();


        JwtService jwtService = new JwtService();
        String token = jwtService.generateToken(data.getEmail(),data.getUsername());
        // Check is this username already exists
        if (userRepository.userExistsByUsername(email)){
            Map<String,String> response = Map.of("email", email, "username", username, "message","User with this username already exists!", "registration", "USERNAME_EXISTS");
            return ResponseEntity.ok()
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                    .body(response).getBody();
        }

        // Check if this email already exists
        if (userRepository.userExistsByEmail(email)){
            Map<String,String> response = Map.of("email", email, "username", username, "message","User with this email already exists!", "registration", "EMAIL_EXISTS");
            return ResponseEntity.ok()
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                    .body(response).getBody();
        }



        // Save this user to the database
        User user = new User(username, token, LocalDateTime.now(), email, firstName, lastName, "", "", hashedPassword, "", studyRole, LocalDateTime.now());
        userRepository.createUser(user);

        // Create Authentication object
        List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_USER"));  // You can adjust the authorities as needed
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                user.getEmail(), null, authorities);

        // Set the authentication in the SecurityContext
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);

        Map<String, String> returnData = Map.of(
                "firstName", firstName,
                "lastName", lastName,
                "email", email,
                "studyRole", studyRole.toString(),
                "token", token,
                "username", username,
                "message", "Registration successful",
                "registration", "REGISTRATION_OK"
        );

        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .body(returnData).getBody();
    }

    @PostMapping(value = "/login", produces = "application/json")
    public Map<String, String> login(@RequestBody Login data) {
        String username = data.getUsername();
        String hashedPassword = data.getHashedPassword().toString();

        // TODO: Check in database if user exists and send a response

        if (userRepository.userExistsByUsername(username)){
            // Username is unique
            User user = userRepository.getUserByUsername(username);
            if(!userRepository.verifyPassword(user,hashedPassword)){
                Map<String,String> response = Map.of("email", username, "passwordCheck", "NOT_OK", "message","Wrong password");
                return ResponseEntity.ok()
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + user.getAccess_Token())
                        .body(response).getBody();
            }
            else{
                Map<String,String> response = Map.of("email", username, "passwordCheck", "OK", "message","Successful login");
                return ResponseEntity.ok()
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + user.getAccess_Token())
                        .body(response).getBody();
            }


        }

        Map<String,String> response = Map.of("email", username, "passwordCheck", "DOESNT_EXIST", "message","User doesn't exist");
        return ResponseEntity.ok()
                .body(response).getBody();
    }
}
