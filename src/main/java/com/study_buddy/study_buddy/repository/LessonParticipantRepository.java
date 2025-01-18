package com.study_buddy.study_buddy.repository;

import com.study_buddy.study_buddy.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface LessonParticipantRepository extends JpaRepository<LessonParticipant, LessonParticipantID> {
    List<LessonParticipant> findByParticipantId(Student participant_id);

    @Modifying
    @Transactional
    @Query("DELETE FROM LessonParticipant lp WHERE lp.participantId.studentId = :studentId AND lp.lessonId.lessonId = :lessonId")
    void deleteByStudentIdAndLessonId(@Param("studentId") Long studentId, @Param("lessonId") Long lessonId);
}
