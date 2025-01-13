package com.study_buddy.study_buddy.service;

import com.study_buddy.study_buddy.dto.LessonDto;
import com.study_buddy.study_buddy.model.*;
import com.study_buddy.study_buddy.repository.LessonRepository;
import com.study_buddy.study_buddy.repository.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LessonService {
    @Autowired
    private LessonRepository lessonRepository;

    @Autowired
    private ProfessorRepository professorRepository;

    public Lesson createLesson(Lesson lesson){ return lessonRepository.save(lesson);}

    public List<Lesson> getAllLessons(){ return lessonRepository.findAll();}

    public List<Lesson> getAllLessonsByProfessor(Professor professor){ return lessonRepository.findByProfessor(professor);}

    public List<Lesson> getAllActiveLessons(){
        List<Lesson> allLessons = lessonRepository.findAll();
        LocalDate today = LocalDate.now();
        LocalTime now = LocalTime.now();

        return allLessons.stream()
                .filter(lesson -> lesson.getDate().isAfter(today) ||
                        (lesson.getDate().isEqual(today)&&lesson.getTime().isAfter(now))
                ).collect(Collectors.toList());

    }

    public List<Lesson> getAllActiveMassLessons(LessonType lessonType){
        List<Lesson> allLessons = lessonRepository.findByLessonType(lessonType);
        LocalDate today = LocalDate.now();
        LocalTime now = LocalTime.now();

        return allLessons.stream()
                .filter(lesson -> lesson.getDate().isAfter(today) ||
                        (lesson.getDate().isEqual(today)&&lesson.getTime().isAfter(now))
                ).collect(Collectors.toList());
    }

    public List<Lesson> getAllFilteredLessons(String parametar){
        List<Lesson> allLessons = lessonRepository.findBySubjectOrLocationIgnoreCase(parametar);
        LocalDate today = LocalDate.now();
        LocalTime now = LocalTime.now();

        return allLessons.stream()
                .filter(lesson -> lesson.getDate().isAfter(today) ||
                        (lesson.getDate().isEqual(today)&&lesson.getTime().isAfter(now))
                ).collect(Collectors.toList());

    }

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

    public LessonDto convertToDto(Lesson lesson){
        // Create lesson and store it into database
        LessonDto lessonDto = new LessonDto();
        lessonDto.setLessonId(lesson.getLessonId());
        lessonDto.setEmail(lesson.getProfessor().getUser().getEmail());
        lessonDto.setUsername(lesson.getProfessor().getUser().getUsername());
        lessonDto.setSubject(lesson.getSubject());
        lessonDto.setDuration(lesson.getDuration());
        lessonDto.setMaxMembers(lesson.getMaxMembers());
        lessonDto.setMinMembers(lesson.getMinMembers());
        lessonDto.setxCoordinate(lesson.getxCoordinate());
        lessonDto.setyCoordinate(lesson.getyCoordinate());
        lessonDto.setLocation(lesson.getLocation());
        lessonDto.setType(lesson.getLessonType());
        lessonDto.setPrice(lesson.getPrice());
        lessonDto.setDate(lesson.getDate());
        lessonDto.setTime(lesson.getTime());
        lessonDto.setRegistrationDeadLine(lesson.getDate().minusDays(2));

        return lessonDto;
    }
}