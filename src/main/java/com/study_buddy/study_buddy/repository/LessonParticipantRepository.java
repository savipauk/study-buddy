package com.study_buddy.study_buddy.repository;

import com.study_buddy.study_buddy.model.GroupMember;
import com.study_buddy.study_buddy.model.GroupMemberID;
import com.study_buddy.study_buddy.model.LessonParticipant;
import com.study_buddy.study_buddy.model.LessonParticipantID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LessonParticipantRepository extends JpaRepository<LessonParticipant, LessonParticipantID> {
    // Custom queries if needed
}
