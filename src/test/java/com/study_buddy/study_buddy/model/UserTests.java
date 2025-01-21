package com.study_buddy.study_buddy.model;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class UserTests {
    private User user;

    @BeforeAll
    static void setUpBeforeClass(){
        System.out.println("Starting Unit tests for class User");
    }

    @AfterAll
    static void tearDownAfterClass(){
        System.out.println("Finished Unit tests for class User");
    }

    @BeforeEach
    public void init() {
        user = new User();
        user.setEmail("test@example.com");
        user.setPassword("password123");
        user.setUsername("janedoe");
        user.setFirstName("Jane");
        user.setLastName("Doe");
        user.setDescription("Math lover!");
        user.setRole(StudyRole.STUDENT);
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        user.setDateOfBirth(LocalDate.of(2003, 1, 1));
        user.setGender(Gender.F);
        user.setCity("New York");
        user.setOauthProvider("");
        user.setOauthId("");
    }

    @Test
    void testUserProperties() throws Exception {
        assertNotNull(user);
        assertEquals(StudyRole.STUDENT, user.getRole());
        assertEquals("Doe", user.getLastName());
    }
}
