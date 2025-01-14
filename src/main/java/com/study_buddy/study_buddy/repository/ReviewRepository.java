package com.study_buddy.study_buddy.repository;

import com.study_buddy.study_buddy.model.Professor;
import com.study_buddy.study_buddy.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    @Query("SELECT AVG(r.rating) FROM Review r WHERE r.professor.professorId = :professorId")
    Float findAverageRatingByProfessorId(@Param("professorId") Long professorId);

    List<Review> findByProfessor(Professor professor);
}
