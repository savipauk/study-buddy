package com.study_buddy.study_buddy.controller;

import org.springframework.web.bind.annotation.*;

import com.study_buddy.study_buddy.dto.Signup;

import java.util.Map;

@RestController
@RequestMapping("/login")
public class LoginController {

    @PostMapping("/oauth")
    public Map<String, String> oauth() {
        return Map.of("yip", "pie");
    }

    @PostMapping("/signup")
    public Map<String, String> signup(@RequestBody Signup signupData) {
        String firstName = signupData.getFirstName();
        String lastName = signupData.getLastName();
        Map<String, String> returnData = Map.of("firstName", firstName, "lastName", lastName);
        return returnData;
    }

    @PostMapping("/login")
    public Map<String, String> login() {
        // Get JWT token
        // Check if user already exists
        // If exists, log in
        // Else create new user and then log in

        return Map.of("yip", "pie");
    }
}
