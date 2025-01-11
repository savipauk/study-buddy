package com.study_buddy.study_buddy.service;

import com.study_buddy.study_buddy.model.Lesson;
import com.study_buddy.study_buddy.model.Professor;
import com.study_buddy.study_buddy.model.StudyGroup;
import com.study_buddy.study_buddy.model.User;
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

    public List<Lesson> deleteAllLessonsByProfessor(User user){
        Professor old_professor = professorRepository.findByUserId(user.getUserId());
        List<Lesson> lessons = lessonRepository.findByProfessor_ProfessorId(old_professor.getProfessorId());
        Professor professor = professorRepository.findByProfessorId(0L);
        for (Lesson lesson : lessons) {
            lesson.setProfessor(professor); // Or set to a placeholder user
            lessonRepository.save(lesson);
        }
        return lessons;
    }
}