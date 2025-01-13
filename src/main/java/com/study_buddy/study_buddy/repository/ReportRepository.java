package com.study_buddy.study_buddy.repository;

import com.study_buddy.study_buddy.model.Report;
import com.study_buddy.study_buddy.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {
    List<Report> findByStatus(Status status);

}