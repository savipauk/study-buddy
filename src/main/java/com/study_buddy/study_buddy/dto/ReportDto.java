package com.study_buddy.study_buddy.dto;

public class ReportDto {
    private String reporterEmail;
    private String reportedEmail;
    private String reason;
    private String reporterUsername;
    private String reportedUsername;

    // Constructors
    public ReportDto(){};

    public ReportDto(String reporterEmail, String reportedEmail, String reason) {
        this.reporterEmail = reporterEmail;
        this.reportedEmail = reportedEmail;
        this.reason = reason;
    }

    // Getters and setters
    public String getReporterEmail() { return reporterEmail; }

    public void setReporterEmail(String reporterEmail) { this.reporterEmail = reporterEmail; }

    public String getReportedEmail() { return reportedEmail; }

    public void setReportedEmail(String reportedEmail) { this.reportedEmail = reportedEmail; }

    public String getReason() { return reason; }

    public void setReason(String reason) { this.reason = reason; }

    public String getReporterUsername() { return reporterUsername; }

    public void setReporterUsername(String reporterUsername) { this.reporterUsername = reporterUsername; }

    public String getReportedUsername() { return reportedUsername; }

    public void setReportedUsername(String reportedUsername) { this.reportedUsername = reportedUsername; }
}
