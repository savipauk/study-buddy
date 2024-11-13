package com.study_buddy.study_buddy.controller;

import com.study_buddy.study_buddy.dto.ProfileResponse;
import java.util.Map;
import com.study_buddy.study_buddy.dto.ProfileUpdate;
import com.study_buddy.study_buddy.dto.ProfileGet;
import com.study_buddy.study_buddy.model.User;
import com.study_buddy.study_buddy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Get all users
    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    // Get user by ID
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        Optional<User> user = userService.getUserById(id);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Create a new user
    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    // Update an existing user
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User updatedUser) {
        try {
            return ResponseEntity.ok(userService.updateUser(id, updatedUser));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete a user
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    // GET /profile - Fetch the user's profile
    @GetMapping(value = "/profile", produces = "application/json")
    public ResponseEntity<ProfileResponse> getProfile(@RequestBody ProfileGet body) {
        // String email = (String
        // SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        // Optional<User> userOpt =
        // Optional.ofNullable(userService.getUserByEmail(body.email));

        // if (userOpt.isEmpty()) {
        // return ResponseEntity.status(404).body(null);
        // }
        //
        // User user = userOpt.get();
        ProfileResponse profileResponse = new ProfileResponse();
        // profileResponse.setUserId(user.getUserId());
        // profileResponse.setEmail(user.getEmail());
        // profileResponse.setFirstName(user.getFirstName());
        // profileResponse.setLastName(user.getLastName());
        // profileResponse.setRole(user.getRole());
        // profileResponse.setDescription(user.getDescription());
        // profileResponse.setAccessToken(user.getAccess_Token());
        profileResponse.setEmail(body.getEmail());
        profileResponse.setFirstName("test");
        profileResponse.setLastName("test");
        profileResponse.setDescription("test");

        // // Retrieve role-specific informatio

        // Optional<Student> studentOpt =
        // userService.getStudentByUserId(user.getUserId());
        // studentOpt.ifPresent(student -> {
        // profileResponse.setCity(student.getCity());
        // profileResponse.setProfilePicture(student.getProfilePicture());
        // });
        // }
        // case "Professor" -> {
        // Optional<Professor> professorOpt =
        // userService.getProfessorByUserId(user.getUserId());
        // professorOpt.ifPresent(professor -> {
        // profileResponse.setCity(professor.getCity());
        // profileResponse.setProfilePicture(professor.getProfilePicture());
        // });
        // }
        // // Add Admin handling if necessary
        // }
        //
        return ResponseEntity.ok(profileResponse);
    }

    // POST /profile - Update the user's profile
    @PostMapping(value = "/profile", produces = "application/json")
    public ResponseEntity<?> updateProfile(@RequestBody ProfileUpdate profileUpdate) {
        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<User> userOpt = Optional.ofNullable(userService.getUserByEmail(email));

        if (userOpt.isEmpty()) {
            return ResponseEntity.status(404).body("User not found");
        }

        User user = userOpt.get();

        // Update common User fields
        user.setFirstName(profileUpdate.getFirstName());
        user.setLastName(profileUpdate.getLastName());
        user.setDescription(profileUpdate.getDescription());
        userService.saveOrUpdateUser(user);

        // Update role-specific fields
        // switch (user.getRole()) {
        // case "Student" -> {
        // Optional<Student> studentOpt =
        // userService.getStudentByUserId(user.getUserId());
        // studentOpt.ifPresent(student -> {
        // student.setCity(profileUpdate.getCity());
        // student.setProfilePicture(profileUpdate.getProfilePicture());
        // student.setDateOfBirth(profileUpdate.getDateOfBirth());
        // student.setGender(profileUpdate.getGender());
        // userService.saveOrUpdateStudent(student);
        // });
        // }
        // case "Professor" -> {
        // Optional<Professor> professorOpt =
        // userService.getProfessorByUserId(user.getUserId());
        // professorOpt.ifPresent(professor -> {
        // professor.setCity(profileUpdate.getCity());
        // professor.setProfilePicture(profileUpdate.getProfilePicture());
        // professor.setDateOfBirth(profileUpdate.getDateOfBirth());
        // professor.setGender(profileUpdate.getGender());
        // userService.saveOrUpdateProfessor(professor);
        // });
        // }
        // // Handle Admin similarly if needed
        // }

        return ResponseEntity.ok("Profile updated successfully");
    }
}
