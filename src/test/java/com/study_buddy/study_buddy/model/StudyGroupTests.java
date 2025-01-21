package com.study_buddy.study_buddy.model;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class StudyGroupTests {
    StudyGroup studyGroup;
    Student student;
    User user;

    @BeforeAll
    static void setUpBeforeClass(){
        System.out.println("Starting Unit tests for class StudyGroup");
    }

    @AfterAll
    static void tearDownAfterClass(){
        System.out.println("Finished Unit tests for class StudyGroup");
    }

    @BeforeEach
    public void init()  {
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
        user.setDateOfBirth(LocalDate.of(2003,1,1));
        user.setGender(Gender.F);
        user.setCity("New York");
        user.setOauthProvider("");
        user.setOauthId("");

        student = new Student();
        student.setUser(user);

        studyGroup = new StudyGroup();
        studyGroup.setCreator(student);
        studyGroup.setGroupName("Advanced Mathematics Study Group");
        studyGroup.setLocation("University Library, Room 301");
        studyGroup.setxCoordinate("40.712776");
        studyGroup.setyCoordinate("-74.005974");
        studyGroup.setDate(LocalDate.of(2025, 2, 15)); // Example date
        studyGroup.setTime(LocalTime.of(15, 0)); // Example time
        studyGroup.setMaxMembers(10);
        studyGroup.setDescription("A group for students who love solving advanced math problems.");
        studyGroup.setExpirationDate(LocalDate.of(2025, 2, 13)); // Registration deadline
    }

    @Test
    void testStudyGroup() throws Exception {
        assertNotNull(studyGroup);
        assertEquals(10, studyGroup.getMaxMembers());
        assertEquals(LocalDate.of(2025, 2, 15),studyGroup.getDate());
    }

    @Test
    void testStudyGroupCreator() throws Exception {
        assertNotNull(studyGroup.getCreator());
        assertEquals(studyGroup.getCreator(),student);
    }
}
