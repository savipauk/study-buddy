package com.study_buddy.study_buddy.repository;

import com.study_buddy.study_buddy.model.StudyGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudyGroupRepository extends JpaRepository<StudyGroup, Long> {

    StudyGroup findByGroupId(Long group_id);
}
