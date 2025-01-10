package com.study_buddy.study_buddy.repository;

import com.study_buddy.study_buddy.model.StudyGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudyGroupRepository extends JpaRepository<StudyGroup, Long> {
    StudyGroup findByGroupId(Long group_id);
    List<StudyGroup> findByCreator_StudentId(Long creator_id);
}
