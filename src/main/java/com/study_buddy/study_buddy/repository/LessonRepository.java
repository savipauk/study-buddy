package com.study_buddy.study_buddy.repository;

import com.study_buddy.study_buddy.model.Lesson;
import com.study_buddy.study_buddy.model.LessonType;
import com.study_buddy.study_buddy.model.Professor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Long> {
    Lesson findByLessonId(Long lesson_id);
    List<Lesson> findByLessonType(LessonType lesson_type);

    // 4) check the professor
    List<Lesson> findByProfessor(Professor professor);
    List<Lesson> findByProfessor_ProfessorId(Long professor_id);


    // Queries for filtarions
    // 1) check the subject 2) check the location 3) check the description
    /*@Query("SELECT l FROM Lesson l WHERE l.subject LIKE %:name% OR l.location LIKE %:name% OR l.description LIKE %:name%")
    List<Lesson> findBySubjectOrLocationOrDescriptionIgnoreCase(@Param("name") String name);*/
    @Query("SELECT l FROM Lesson l WHERE LOWER(l.subject) LIKE LOWER(CONCAT('%', :name, '%')) " +
            "OR LOWER(l.location) LIKE LOWER(CONCAT('%', :name, '%')) ")
    List<Lesson> findBySubjectOrLocationIgnoreCase(@Param("name") String name);


}