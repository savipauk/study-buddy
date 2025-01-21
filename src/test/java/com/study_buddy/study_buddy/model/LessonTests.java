package com.study_buddy.study_buddy.model;

import jakarta.persistence.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class LessonTests {

    private Lesson lesson;
    private Professor professor;
    private User user;

    @BeforeAll
    static void setUpBeforeClass(){
        System.out.println("Starting Unit tests for class Lesson");
    }

    @AfterAll
    static void tearDownAfterClass(){
        System.out.println("Finished Unit tests for class Lesson");
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
        user.setRole(StudyRole.PROFESSOR);
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        user.setDateOfBirth(LocalDate.of(2003,1,1));
        user.setGender(Gender.F);
        user.setCity("New York");
        user.setOauthProvider("");
        user.setOauthId("");

        professor = new Professor();
        professor.setUser(user);

        lesson = new Lesson();
        lesson.setSubject("Mathematics");
        lesson.setDuration("2 hours");
        lesson.setMaxMembers(30);
        lesson.setMinMembers(5);
        lesson.setxCoordinate("40.712776");
        lesson.setyCoordinate("-74.005974");
        lesson.setLocation("New York City, NY");
        lesson.setLessonType(LessonType.MASS);
        lesson.setPrice("50.00 USD");
        lesson.setDate(LocalDate.of(2025, 2, 25));
        lesson.setTime(LocalTime.of(10, 0));
        lesson.setRegistrationDeadline(LocalDate.of(2025, 2, 23));
        lesson.setProfessor(professor);
    }


    @Test
    void testLessonProperties() throws Exception {
        assertNotNull(lesson);
        assertEquals(LessonType.MASS, lesson.getLessonType());
        assertEquals(lesson.getMaxMembers(),30);
    }

    @Test
    void testLessonProfessor() throws Exception {
        assertNotNull(lesson.getProfessor());
        assertEquals(lesson.getProfessor(), professor);
    }
}
