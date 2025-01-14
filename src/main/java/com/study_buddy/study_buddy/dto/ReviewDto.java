package com.study_buddy.study_buddy.dto;

import java.time.LocalDateTime;

public class ReviewDto {
    private String studentUsername;
    private String professorUsername;
    private int rating;
    private String comment;
    private LocalDateTime reviewDateTime;
    private Long reviewId;

    // Constructors
    public ReviewDto() {}

    public ReviewDto(String studentUsername, String professorUsername, int rating, String comment, LocalDateTime reviewDate) {
        this.studentUsername = studentUsername;
        this.professorUsername = professorUsername;
        this.rating = rating;
        this.comment = comment;
        this.reviewDateTime = reviewDate;
    }

    // Getters and setters
    public String getStudentUsername() { return studentUsername; }

    public void setStudentUsername(String studentUsername) { this.studentUsername = studentUsername; }

    public String getProfessorUsername() { return professorUsername; }

    public void setProfessorUsername(String professorUsername) { this.professorUsername = professorUsername; }

    public int getRating() { return rating; }

    public void setRating(int rating) { this.rating = rating; }

    public String getComment() { return comment; }

    public void setComment(String comment) { this.comment = comment; }

    public LocalDateTime getReviewDateTime() { return reviewDateTime; }

    public void setReviewDateTime(LocalDateTime reviewDateTime) { this.reviewDateTime = reviewDateTime; }

    public Long getReviewId() { return reviewId; }

    public void setReviewId(Long reviewId) { this.reviewId = reviewId; }
}
