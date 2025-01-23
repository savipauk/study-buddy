package com.study_buddy.study_buddy.service;

import com.study_buddy.study_buddy.model.Lesson;
import com.study_buddy.study_buddy.model.LessonType;
import com.study_buddy.study_buddy.model.Professor;
import com.study_buddy.study_buddy.repository.LessonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LessonServiceTests {

    @Mock
    private LessonRepository lessonRepository;

    @InjectMocks
    private LessonService lessonService;

    private Lesson lesson;
    private Lesson lesson2;
    private Professor professor;
    private Professor professor2;

    @BeforeEach
    public void init(){
         lesson = new Lesson();
         lesson.setLessonId(1L); // Set a unique ID
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

         professor = new Professor();
         professor.setProfessorId(1L);
    }

    @Test
    public void LessonService_GetAllLessons_ReturnLesson(){
        when(lessonRepository.findAll()).thenReturn(Arrays.asList(lesson));

        List<Lesson> lessonList = lessonService.getAllLessons();

        assertNotNull(lessonList);
    }

}


