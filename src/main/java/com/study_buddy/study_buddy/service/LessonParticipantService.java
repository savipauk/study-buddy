package com.study_buddy.study_buddy.service;


import com.study_buddy.study_buddy.model.Lesson;
import com.study_buddy.study_buddy.model.LessonParticipant;
import com.study_buddy.study_buddy.model.LessonParticipantID;
import com.study_buddy.study_buddy.model.Student;
import com.study_buddy.study_buddy.repository.LessonParticipantRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class LessonParticipantService {

    private final LessonParticipantRepository lessonParticipantRepository;

    public LessonParticipantService(LessonParticipantRepository lessonParticipantRepository) {
        this.lessonParticipantRepository = lessonParticipantRepository;
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
}