package com.study_buddy.study_buddy.repository;

import com.study_buddy.study_buddy.model.Professor;
import com.study_buddy.study_buddy.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfessorRepository extends JpaRepository<Professor, Long> {
    Professor findByProfessorId(Long professor_id);
    Professor findByUserId(Long user_id);

}
