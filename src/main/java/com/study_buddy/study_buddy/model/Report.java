package com.study_buddy.study_buddy.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Reports")
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="report_id", nullable = false)
    private Long reportId;

    @Column(name = "reason", nullable = false, length = 255)
    private String reason;

    @Column(name = "report_date")
    private LocalDateTime reportDateTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    // CONNECTING TABLES USERS-REPORTS (user is the reporter)
    @ManyToOne
    @JoinColumn(name = "reporter_id", nullable = false)
    private User reporter;

    // CONNECTING TABLES USERS-REPORTS (user is the reported user)
    @ManyToOne
    @JoinColumn(name = "reported_user_id", nullable = false)
    private User reportedUser;




    // Constructors
    public Report() {}

    public Report(Long reportId, User reporter, User reportedUser, String reason, LocalDateTime reportDate) {
        this.reportId = reportId;
        this.reporter = reporter;
        this.reportedUser = reportedUser;
        this.reason = reason;
        this.reportDateTime = reportDate;
    }

    // Getters and setters
    public Long getReportId() {
        return reportId;
    }

    public void setReportId(Long reportId) {
        this.reportId = reportId;
    }

    public User getReporter() {
        return reporter;
    }

    public void setReporter(User reporter) {
        this.reporter = reporter;
    }

    public User getReportedUser() {
        return reportedUser;
    }

    public void setReportedUser(User reportedUser) {
        this.reportedUser = reportedUser;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public LocalDateTime getReportDateTime() {
        return reportDateTime;
    }

    public void setReportDateTime(LocalDateTime reportDateTime) {
        this.reportDateTime = reportDateTime;
    }

    public Status getStatus() { return status; }

    public void setStatus(Status status) { this.status = status; }
}



