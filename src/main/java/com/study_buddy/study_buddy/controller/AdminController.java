package com.study_buddy.study_buddy.controller;

import com.study_buddy.study_buddy.dto.ReportDto;
import com.study_buddy.study_buddy.model.Report;
import com.study_buddy.study_buddy.model.Status;
import com.study_buddy.study_buddy.model.User;
import com.study_buddy.study_buddy.service.ReportService;
import com.study_buddy.study_buddy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final ReportService reportService;

    @Autowired
    public AdminController(UserService userService, ReportService reportService) {
        this.userService = userService;
        this.reportService = reportService;
    }

    // Getting reports
    @GetMapping("/allReports")
    public ResponseEntity<List<ReportDto>> getAllReports(){
        List<Report> reports = reportService.getAllReports();
        List<ReportDto> reportDtos = reports.stream()
                .map(reportService::convertToDto)
                .toList();
        return ResponseEntity.ok(reportDtos);
    };

    // Getting reports depending on status
    @GetMapping("/allStatusReports/{status}")
    public ResponseEntity<List<ReportDto>> getAllOpenReports(@PathVariable("status") Status status){
        List<Report> reports = reportService.getAllByReportStatus(status);
        List<ReportDto> reportDtos = reports.stream()
                .map(reportService::convertToDto)
                .toList();
        return ResponseEntity.ok(reportDtos);
    };

    // Changing status of report to rejected
    @PostMapping("/rejectedReport/{reportId}")
    public ResponseEntity<String> changeStatusToRejected(@PathVariable("reportId") Long reportId){
        Report report = reportService.getReportByReportId(reportId);
        report.setStatus(Status.REJECTED);
        reportService.createOrUpdateReport(report);
        return ResponseEntity.ok("UPDATED");
    }


    // Creating report
    @PostMapping("/submit-report")
    public ResponseEntity<String> createReport(@RequestBody ReportDto dto) {
        User reporterUser = userService.getUserByEmail(dto.getReporterEmail());
        if (reporterUser==null) {
            return ResponseEntity.noContent().build();
        }
        User reportedUser = userService.getUserByEmail(dto.getReportedEmail());
        if (reportedUser==null) {
            return ResponseEntity.noContent().build();
        }
        LocalDateTime now = LocalDateTime.now();

        Report report = new Report();
        report.setReportDateTime(now);
        report.setReporter(reporterUser);
        report.setReportedUser(reportedUser);
        report.setReason(dto.getReason());
        report.setStatus(Status.OPEN);

        reportService.createOrUpdateReport(report);

        return ResponseEntity.ok("REPORT_CREATED");
    }


}
