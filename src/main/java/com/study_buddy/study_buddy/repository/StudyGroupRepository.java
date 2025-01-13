package com.study_buddy.study_buddy.repository;

import com.study_buddy.study_buddy.model.Student;
import com.study_buddy.study_buddy.model.StudyGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudyGroupRepository extends JpaRepository<StudyGroup, Long> {

    StudyGroup findByGroupId(Long group_id);
    List<StudyGroup> findByCreator_StudentId(Long creator_id);
    List<StudyGroup> findByCreator(Student creator);

    // Queries for filtration
    // 1) check the groupName 2) check the location 3) check the description
    @Query("SELECT s FROM StudyGroup s WHERE LOWER(s.groupName) LIKE LOWER(CONCAT('%', :name, '%')) " +
            "OR LOWER(s.location) LIKE LOWER(CONCAT('%', :name, '%')) " +
            "OR LOWER(s.description) LIKE LOWER(CONCAT('%', :name, '%')) ")
    List<StudyGroup> findByGroupNameOrLocationOrDescriptionIgnoreCase(@Param("name") String name);
}
