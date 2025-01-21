package com.study_buddy.study_buddy.model;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class EnumTests {

    @Test
    void testGenderEnumValue() throws Exception{
        assertEquals("NOTDEFINED", Gender.NOTDEFINED.getValue());
        assertEquals("M", Gender.M.getValue());
        assertEquals("F", Gender.F.getValue());
    }

    @Test
    void testGenderEnumToString() throws Exception{
        assertEquals("NOTDEFINED", Gender.NOTDEFINED.toString());
        assertEquals("M", Gender.M.toString());
        assertEquals("F", Gender.F.toString());
    }

    @Test
    void testLessonTypeEnumValue() throws Exception{
        assertEquals("MASS", LessonType.MASS.getValue());
        assertEquals("ONE_ON_ONE", LessonType.ONE_ON_ONE.getValue());
    }

    @Test
    void testLessonTypeEnumToString() throws Exception{
        assertEquals("MASS", LessonType.MASS.toString());
        assertEquals("ONE_ON_ONE", LessonType.ONE_ON_ONE.toString());
    }

    @Test
    void testStatusEnumValue() throws Exception{
        assertEquals("OPEN", Status.OPEN.getValue());
        assertEquals("CLOSED", Status.CLOSED.getValue());
        assertEquals("IN_PROGRESS", Status.IN_PROGRESS.getValue());
        assertEquals("REJECTED", Status.REJECTED.getValue());
    }

    @Test
    void testStatusEnumToString() throws Exception{
        assertEquals("OPEN", Status.OPEN.toString());
        assertEquals("CLOSED", Status.CLOSED.toString());
        assertEquals("IN_PROGRESS", Status.IN_PROGRESS.toString());
        assertEquals("REJECTED", Status.REJECTED.toString());
    }

    @Test
    void testStudyRoleEnumValue() throws Exception{
       assertEquals("UNDEFINED", StudyRole.UNDEFINED.getValue());
       assertEquals("STUDENT", StudyRole.STUDENT.getValue());
       assertEquals("PROFESSOR", StudyRole.PROFESSOR.getValue());
       assertEquals("ADMIN", StudyRole.ADMIN.getValue());
       assertEquals("UNASSIGNED", StudyRole.UNASSIGNED.getValue());
    }

    @Test
    void testStudyRoleEnumToString() throws Exception{
        assertEquals("UNDEFINED", StudyRole.UNDEFINED.toString());
        assertEquals("STUDENT", StudyRole.STUDENT.toString());
        assertEquals("PROFESSOR", StudyRole.PROFESSOR.toString());
        assertEquals("ADMIN", StudyRole.ADMIN.toString());
        assertEquals("UNASSIGNED", StudyRole.UNASSIGNED.toString());
    }
}
