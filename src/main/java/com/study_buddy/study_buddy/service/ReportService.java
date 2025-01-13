package com.study_buddy.study_buddy.service;


import com.study_buddy.study_buddy.dto.ReportDto;
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

    public Report getReportByReportId(Long reportId) { return reportRepository.findById(reportId).get();}

    public Report createOrUpdateReport(Report report){ return reportRepository.save(report);}

    public List<Report> getAllReports() { return reportRepository.findAll();}

    public List<Report> getAllByReportStatus(Status status) { return reportRepository.findByStatus(status);}


    public ReportDto convertToDto(Report report) {
        ReportDto dto = new ReportDto();
        dto.setReporterEmail(report.getReporter().getEmail());
        dto.setReporterUsername(report.getReporter().getUsername());
        dto.setReportedEmail(report.getReportedUser().getEmail());
        dto.setReportedUsername(report.getReportedUser().getUsername());
        dto.setReason(report.getReason());
        dto.setStatus(report.getStatus());
        dto.setReportId(report.getReportId());
        return dto;
    }
}
