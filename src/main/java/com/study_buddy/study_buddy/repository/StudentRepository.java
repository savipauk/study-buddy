package com.study_buddy.study_buddy.repository;

import com.study_buddy.study_buddy.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    Student findByStudentId(Long student_id);
    Student findByUserId(Long user_id);
}
