package com.study_buddy.study_buddy.service;


import com.study_buddy.study_buddy.model.Report;
import com.study_buddy.study_buddy.model.Status;
import com.study_buddy.study_buddy.repository.ReportRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportService {

    private final ReportRepository reportRepository;

    public ReportService(ReportRepository reportRepository){
        this.reportRepository = reportRepository;
    }

    public Report createReport(Report report){ return reportRepository.save(report);}

    public List<Report> getAllReports() { return reportRepository.findAll();}

    public List<Report> getAllByReportStatus(Status status) { return reportRepository.findByStatus(status);}
}
