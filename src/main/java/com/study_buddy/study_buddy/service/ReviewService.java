package com.study_buddy.study_buddy.service;

import com.study_buddy.study_buddy.dto.ReviewDto;
import com.study_buddy.study_buddy.model.Professor;
import com.study_buddy.study_buddy.model.Review;
import com.study_buddy.study_buddy.repository.ReviewRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;

    public ReviewService(ReviewRepository reviewRepository){ this.reviewRepository = reviewRepository;}

    public Review getReviewById(Long reviewId) { return reviewRepository.findById(reviewId).get();}

    public List<Review> getReviewByProfessor(Professor professor) { return reviewRepository.findByProfessor(professor);}

    public Review saveOrUpdate(Review review) { return reviewRepository.save(review);}

    public List<Review> getAllReviews() { return reviewRepository.findAll();}

    public  Float getAverageRatingForProfessor(Long professorId) { return reviewRepository.findAverageRatingByProfessorId(professorId);}

    public ReviewDto convertToDto(Review review){
        ReviewDto dto = new ReviewDto();
        dto.setReviewId(review.getReviewId());
        dto.setComment(review.getComment());
        dto.setRating(review.getRating());
        dto.setReviewDateTime(review.getReviewDateTime());
        dto.setStudentUsername(review.getStudent().getUser().getUsername());
        dto.setProfessorUsername(review.getProfessor().getUser().getUsername());

        return dto;
    }

}
