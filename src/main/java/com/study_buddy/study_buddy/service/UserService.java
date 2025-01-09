package com.study_buddy.study_buddy.service;

import com.study_buddy.study_buddy.model.User;
import com.study_buddy.study_buddy.repository.UserRepository;
import com.study_buddy.study_buddy.dto.Profile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        // this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    private StudentService studentService;

    @Autowired
    private ProfessorService professorService;

    // Password Encoder
    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

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





    public Profile buildProfileResponse(User user) {
        Profile profile = new Profile();

        profile.setEmail(user.getEmail());
        profile.setFirstName(user.getFirstName());
        profile.setLastName(user.getLastName());
        profile.setRole(user.getRole());
        profile.setDescription(user.getDescription());
        profile.setUsername(user.getUsername());
        profile.setPassword(user.getPassword());
        profile.setGender(user.getGender());
        profile.setCity(user.getCity());
        profile.setDateOfBirth(user.getDateOfBirth());

        return profile;
    }

    // Joining new changed data from new_user with old unchanged data from old_user
    public User updateUserProfile(User old_user, User new_user){
        // USER_ID, Oauth_provider, oauth_id, created_at and tokens are determined by old_user

        // email is always given in new_user
        old_user.setEmail(new_user.getEmail());

        // Setting up new password if password is changed
        if(new_user.getPassword() != null){ old_user.setPassword(new_user.getPassword()); }

        // Saving changes only if something was changed
        if(new_user.getDescription() != null) { old_user.setDescription(new_user.getDescription()); }
        if(new_user.getProfilePicture() != null) { old_user.setProfilePicture(new_user.getProfilePicture()); }
        if(new_user.getFirstName() != null) { old_user.setFirstName(new_user.getFirstName()); }
        if(new_user.getLastName() != null) { old_user.setLastName(new_user.getLastName()); }
        if(new_user.getUsername() != null) {  old_user.setUsername(new_user.getUsername()); }

        // Setting up profile for oauth registration requires role to be given in new_user
        if (new_user.getRole() != null) { old_user.setRole(new_user.getRole()); }
        if (new_user.getGender() !=null) { old_user.setGender(new_user.getGender());}
        if (new_user.getCity() != null) { old_user.setCity(new_user.getCity());}
        if (new_user.getDateOfBirth() != null) { old_user.setDateOfBirth(new_user.getDateOfBirth());}

        // updated_at is current date and time
        old_user.setUpdatedAt(LocalDateTime.now());

        userRepository.save(old_user);

        // ADDING STUDENT AND PROFESSOR INTO TABLES
        if (new_user.getRole() != null && new_user.getRole().name().equals("STUDENT")) {
            studentService.createStudent(old_user);
        }
        else if (new_user.getRole() != null && new_user.getRole().name().equals("PROFESSOR")) {
            professorService.createProfessor(old_user);
        }

        return old_user;
    }
}

