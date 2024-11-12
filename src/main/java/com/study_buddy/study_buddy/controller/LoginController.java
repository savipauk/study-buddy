package com.study_buddy.study_buddy.controller;

import java.time.LocalDateTime;
import java.util.HashMap;
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
        System.out.println(hashedPassword.toString());
        // Example logic for generating a JWT after registering a user
        JwtService jwtService = new JwtService();
        String token = jwtService.generateToken(data.getEmail());  // Implement JWT generation

        // Save this user to the database
        User user = new User(token, LocalDateTime.now(), email, firstName, lastName, "", "", hashedPassword, "", studyRole, LocalDateTime.now());
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
                "hashedPassword", hashedPassword.toString(),
                "studyRole", studyRole.toString(),
                "token", token,
                "message", "Registration successful"
        );

        // Send the token back to the client
        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)  // Optional: set in header
                .body(returnData).getBody();
    }

    /*@PostMapping(value = "/login/register", produces = "application/json")
    public Map<String, String> register(@RequestBody Register data) {
        System.out.println(data.getEmail());
        String email = data.getEmail();
        String firstName = data.getFirstName();
        String lastName = data.getLastName();
        String hashedPassword = data.getHashedPassword();
        String studyRole = data.getStudyRole();

        // Example logic for generating a JWT after registering a user
        JwtService jwtService = new JwtService();
        String token = jwtService.generateToken(data.getEmail());  // Implement JWT generation


        // TODO: Save this user to the database
        //User user = new User();
        User user = new User(token,LocalDateTime.now(),email,firstName,lastName,"","",hashedPassword,"",studyRole,LocalDateTime.now());
        System.out.println(user);
        System.out.println(user.getEmail());
        //User user = new User("",LocalDateTime.now(),email,firstName,lastName,"","",hashedPassword,"",studyRole,LocalDateTime.now());
        userRepository.createUser(user);
        Map<String, String> returnData = Map.of("firstName", firstName, "lastName", lastName, "email", email,
                "hashedPassword", hashedPassword.toString(), "studyRole", studyRole, "token",token, "message", "Registration successful");



       *//* Map<String, String> response = new HashMap<>();
        response.put("token", token);
        response.put("message", "Registration successful");*//*

        // Send the token back to the client
        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)  // Optional: set in header
                .body(returnData).getBody();

    }*/

    @PostMapping(value = "/login", produces = "application/json")
    public Map<String, String> login(@RequestBody Login data) {
        String email = data.getEmail();
        String hashedPassword = data.getHashedPassword();

        // TODO: Check in database if user exists and send a response

        return Map.of("email", email, "hashedPassword", hashedPassword.toString());
    }
}
