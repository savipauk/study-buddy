package com.study_buddy.study_buddy.service;

import com.study_buddy.study_buddy.model.Lesson;
import com.study_buddy.study_buddy.model.StudyGroup;
import com.study_buddy.study_buddy.repository.LessonRepository;
import com.study_buddy.study_buddy.repository.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LessonService {
    @Autowired
    private LessonRepository lessonRepository;

    @Autowired
    private ProfessorRepository professorRepository;

    public Lesson createLesson(Lesson lesson){ return lessonRepository.save(lesson);}

    public List<Lesson> getAllLessons(){ return lessonRepository.findAll();}

    public Lesson getLessonById(Long lessonId){ return lessonRepository.findByLessonId(lessonId);}

}