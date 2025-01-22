package com.study_buddy.study_buddy.model;

import jakarta.persistence.*;
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
public class ReportTests {
    private User user;
    private User newUser;
    private Report report;

    @BeforeAll
    static void setUpBeforeClass(){
        System.out.println("Starting Unit tests for class Report");
    }

    @AfterAll
    static void tearDownAfterClass(){
        System.out.println("Finished Unit tests for class Report");
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
        user.setStatus(Status.ACTIVE);

        newUser = new User();
        newUser.setEmail("exampleuser@example.com");
        newUser.setPassword("securepassword456");
        newUser.setUsername("johnsmith");
        newUser.setFirstName("John");
        newUser.setLastName("Smith");
        newUser.setDescription("Passionate about programming and technology.");
        newUser.setRole(StudyRole.PROFESSOR);
        newUser.setCreatedAt(LocalDateTime.now());
        newUser.setUpdatedAt(LocalDateTime.now());
        newUser.setDateOfBirth(LocalDate.of(1990, 5, 15));
        newUser.setGender(Gender.M);
        newUser.setCity("San Francisco");
        newUser.setOauthProvider("Google");
        newUser.setOauthId("12345-google-oauth");
        newUser.setStatus(Status.ACTIVE);

        report = new Report();
        report.setStatus(Status.OPEN);
        report.setReportDateTime(LocalDateTime.now());
        report.setReason("Inappropriate comments!");
        report.setReporter(user);
        report.setReportedUser(newUser);
    }

    @Test
    public void testReportProperties() throws Exception{
        assertNotNull(report);
        assertEquals(Status.OPEN, report.getStatus());
        assertEquals(user, report.getReporter());
        assertEquals(newUser,report.getReportedUser());
    }
}
