package com.study_buddy.study_buddy.service;


import com.study_buddy.study_buddy.model.Lesson;
import com.study_buddy.study_buddy.model.LessonParticipant;
import com.study_buddy.study_buddy.model.LessonParticipantID;
import com.study_buddy.study_buddy.model.Student;
import com.study_buddy.study_buddy.repository.LessonParticipantRepository;
import com.study_buddy.study_buddy.repository.LessonRepository;
import com.study_buddy.study_buddy.repository.StudentRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class LessonParticipantService {

    private final LessonParticipantRepository lessonParticipantRepository;
    private final LessonRepository lessonRepository;
    private final StudentRepository studentRepository;

    public LessonParticipantService(LessonParticipantRepository lessonParticipantRepository,
                                    LessonRepository lessonRepository, StudentRepository studentRepository) {
        this.lessonParticipantRepository = lessonParticipantRepository;
        this.lessonRepository = lessonRepository;
        this.studentRepository = studentRepository;
    }

    //Add a student to a lesson.
    @Transactional
    public void addStudentToLesson(Student student, Lesson lesson) {
        // Check if the student is already a member
        LessonParticipantID lessonParticipantID = new LessonParticipantID(student.getStudentId(), lesson.getLessonId());
        if (lessonParticipantRepository.existsById(lessonParticipantID)) {
            throw new IllegalStateException("Student is already a member of this group!");
        }

        // Create a new GroupMember entity
        LessonParticipant lessonParticipant = new LessonParticipant(student, lesson);

        // Persist the relationship
        lessonParticipantRepository.save(lessonParticipant);
    }

    public List<LessonParticipant> getLessonsByParticipantId(Student participantId){ return lessonParticipantRepository.findByParticipantId(participantId);}

    @Transactional
    public void deleteByLessonIdAndStudentId(Long lessonId, Long studentId) {
        // Remove directly via repository if applicable
        lessonParticipantRepository.deleteByStudentIdAndLessonId(studentId, lessonId);

        // Flush and clear the persistence context to avoid conflicts
        /*entityManager.flush();
        entityManager.clear();*/

        // Optionally fetch and update entities
        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new EntityNotFoundException("Study group not found"));
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new EntityNotFoundException("Student not found"));

        // Update relationships
        lesson.getStudentParticipants().remove(student);
        student.getLessons().remove(lesson);

        // Persist changes
        lessonRepository.save(lesson);
        studentRepository.save(student);
    }
}