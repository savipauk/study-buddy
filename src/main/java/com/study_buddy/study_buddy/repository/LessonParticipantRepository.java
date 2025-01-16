package com.study_buddy.study_buddy.repository;

import com.study_buddy.study_buddy.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LessonParticipantRepository extends JpaRepository<LessonParticipant, LessonParticipantID> {
    List<LessonParticipant> findByParticipantId(Student participant_id);
}
