package com.study_buddy.study_buddy.service;

import com.study_buddy.study_buddy.model.Gender;
import com.study_buddy.study_buddy.model.StudyRole;
import com.study_buddy.study_buddy.model.User;
import com.study_buddy.study_buddy.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class UserServiceTests {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    public void UserService_CreateUser_ReturnsUser(){
        User user = new User();
        user.setUserId(1L);
        user.setEmail("test@example.com");
        user.setPassword("password123");
        user.setUsername("janedoe");
        user.setFirstName("Jane");
        user.setLastName("Doe");
        user.setDescription("Math lover!");
        user.setRole(StudyRole.STUDENT);
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        user.setDateOfBirth(LocalDate.of(2003,1,1));
        user.setGender(Gender.F);
        user.setCity("New York");
        user.setOauthProvider("");
        user.setOauthId("");

        User user_dto = new User();
        user_dto.setUserId(1L);
        user_dto.setEmail("test@example.com");
        user_dto.setPassword("password123");
        user_dto.setUsername("janedoe");
        user_dto.setFirstName("Jane");
        user_dto.setLastName("Doe");
        user_dto.setDescription("Math lover!");
        user_dto.setRole(StudyRole.STUDENT);
        user_dto.setCreatedAt(LocalDateTime.now());
        user_dto.setUpdatedAt(LocalDateTime.now());
        user_dto.setDateOfBirth(LocalDate.of(2003,1,1));
        user_dto.setGender(Gender.F);
        user_dto.setCity("New York");
        user_dto.setOauthProvider("");
        user_dto.setOauthId("");

        when(userRepository.save(Mockito.any(User.class))).thenReturn(user);

        User savedUser = userService.createUser(user_dto);
        assertNotNull(savedUser);
        assertEquals(user.getEmail(),savedUser.getEmail());
    }
}
