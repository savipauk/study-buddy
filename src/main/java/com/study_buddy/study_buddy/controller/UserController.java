package com.study_buddy.study_buddy.controller;

import com.study_buddy.study_buddy.dto.ProfileUpdate;
import com.study_buddy.study_buddy.model.User;
import com.study_buddy.study_buddy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import com.study_buddy.study_buddy.dto.Profile;

import java.util.List;
import java.util.Map;
import java.util.Objects;
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

    // TODO: fix updateUser - currently not in use

    // Update an existing user
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User updatedUser) {
        try {
            return ResponseEntity.ok(userService.updateUser(id, updatedUser));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // TODO: fix deleteUser - currently not in use

    // Delete a user
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }



    // GET /profile - Fetch the user's profile
    @GetMapping(value = "/profile/{username}", produces = "application/json")
    public Map<String ,String> getProfile(@PathVariable("email") String email) {

        Optional<User> userOpt = Optional.ofNullable(userService.getUserByEmail(email));
        if (userOpt.isEmpty()) {
            return Map.of("message", "PROFILE_DOESNT_EXIST");
        }

        User user = userOpt.get();
        Profile profile = userService.buildProfileResponse(user);

        return profile.profileResponse("PROFILE_LOADED");
    }



    // POST /profile - Update the user's profile
    @PostMapping(value = "/profile/update/{email}", produces = "application/json")
    public Map<String,String> updateProfile(@PathVariable("email") String email, @RequestBody User new_user) {
        User old_user = userService.getUserByEmail(email);

        // Check is this username already exists
        if (userService.userExistsByUsername(new_user.getUsername())) {
            if(!Objects.equals(new_user.getUsername(), old_user.getUsername())){
                return ResponseEntity.ok()
                        //.header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .body(old_user.response("USERNAME_EXISTS")).getBody();
            }

        }

        // Check if this email already exists
        if (userService.userExistsByEmail(new_user.getEmail())) {
            if(!Objects.equals(new_user.getEmail(), old_user.getEmail())){
                return ResponseEntity.ok()
                        //.header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .body(old_user.response("EMAIL_EXISTS")).getBody();
            }
        }


        // Joining new changed data from new_user with old unchanged data from old_user
        new_user = new_user.setChanges(old_user, new_user);




        userService.saveOrUpdateUser(new_user);
        return ResponseEntity.ok()
                //.header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .body(new_user.response("UPDATE_OK")).getBody();

    }
}


