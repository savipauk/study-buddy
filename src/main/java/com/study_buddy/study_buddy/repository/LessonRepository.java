package com.study_buddy.study_buddy.repository;

import com.study_buddy.study_buddy.model.Lesson;
import com.study_buddy.study_buddy.model.StudyGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Long> {
    Lesson findByLessonId(Long lesson_id);
}