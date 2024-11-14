package com.study_buddy.study_buddy.service;

import com.study_buddy.study_buddy.model.User;
import com.study_buddy.study_buddy.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    // TODO: Add password encoder

    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        // this.passwordEncoder = passwordEncoder;
    }

    // Fetch all users
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Fetch a single user by ID
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    // Fetch a user by email
    public User getUserByEmail(String email) { return userRepository.findByEmail(email);}

    // Fetch a user by username
    public User getUserByUsername(String username) { return userRepository.findByUsername(username);}

    // Check if user exists by username
    public boolean userExistsByUsername(String username) { return userRepository.findByUsername(username)!=null;}

    // Check if user exists by email
    public boolean userExistsByEmail(String email) { return getUserByEmail(email) != null; }

    // Save or update a user
    public User saveOrUpdateUser(User user) {
        // TODO: Hash the password if it is new or has been updated
//        if (user.getPassword() != null) {
//            user.setPassword(passwordEncoder.encode(user.getPassword()));
//        }
        return userRepository.save(user);
    }

    // Create a new user
    public User createUser(User user) {
        return userRepository.save(user);
    }


    // Verify the provided password with the stored hashed password
    public boolean verifyPassword(User user, String rawPassword) {
        return passwordEncoder.matches(rawPassword, user.getPassword());
    }

    // Update an existing user
    public User updateUser(Long id, User updatedUser) {
        Optional<User> existingUserOpt = userRepository.findById(id);
        if (existingUserOpt.isPresent()) {
            User existingUser = existingUserOpt.get();
            existingUser.setEmail(updatedUser.getEmail());
            existingUser.setUserId(updatedUser.getUserId());
            existingUser.setFirstName(updatedUser.getFirstName());
            existingUser.setLastName(updatedUser.getLastName());

            return userRepository.save(existingUser);

        } else {
            throw new RuntimeException("User not found with id: " + id);
        }
    }

    // Delete a user by ID
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}

