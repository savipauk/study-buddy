package com.study_buddy.study_buddy.controller;

import com.study_buddy.study_buddy.dto.LessonDto;
import com.study_buddy.study_buddy.dto.ReviewDto;
import com.study_buddy.study_buddy.model.Professor;
import com.study_buddy.study_buddy.model.Review;
import com.study_buddy.study_buddy.model.Student;
import com.study_buddy.study_buddy.model.User;
import com.study_buddy.study_buddy.service.ProfessorService;
import com.study_buddy.study_buddy.service.ReviewService;
import com.study_buddy.study_buddy.service.StudentService;
import com.study_buddy.study_buddy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    private final UserService userService;
    private final ReviewService reviewService;
    private final StudentService studentService;
    private final ProfessorService professorService;

    @Autowired
    public ReviewController(UserService userService, ReviewService reviewService, StudentService studentService, ProfessorService professorService) {
        this.userService = userService;
        this.reviewService = reviewService;
        this.studentService = studentService;
        this.professorService = professorService;
    }

    // Display all reviews
    @GetMapping("/allReviews")
    public ResponseEntity<List<ReviewDto>> getAllReviews(){
        List<Review> reviews = reviewService.getAllReviews();

        List<ReviewDto> reviewDtos = reviews.stream()
                .map(reviewService::convertToDto)
                .toList();
        return ResponseEntity.ok(reviewDtos);
    }

    // Display all reviews to professor
    @GetMapping("/allReviews/{username}")
    public ResponseEntity<List<ReviewDto>> getAllReviewsForProfessor(@PathVariable("username") String username){
        User user = userService.getUserByUsername(username);
        if (user==null) {
            return ResponseEntity.noContent().build();
        }
        Professor professor = professorService.getProfessorByUserId(user.getUserId());

        List<Review> reviews = reviewService.getReviewByProfessor(professor);

        List<ReviewDto> reviewDtos = reviews.stream()
                .map(reviewService::convertToDto)
                .toList();
        return ResponseEntity.ok(reviewDtos);
    }

    // Get professor's average rating
    @GetMapping("/averageRating/{username}")
    public Map<String ,Float> getProfessorsAverageRating(@PathVariable("username") String username){
        User user = userService.getUserByUsername(username);
        if (user==null) {
            return Map.of("averageRating", null);
        }
        Professor professor = professorService.getProfessorByUserId(user.getUserId());
        Float averageRating = reviewService.getAverageRatingForProfessor(professor.getProfessorId());

        return Map.of("averageRating", averageRating);
    }

    // Create review
    @PostMapping("/create")
    public ResponseEntity<String> createReview(@RequestBody ReviewDto dto){
        User user_student;
        if(dto.getStudentEmail()!=null){ user_student = userService.getUserByEmail(dto.getStudentEmail());
        } else { user_student = userService.getUserByUsername(dto.getStudentUsername()); }
        if (user_student==null) { return ResponseEntity.notFound().build(); }

        User user_professor;
        if(dto.getProfessorEmail()!=null){ user_professor = userService.getUserByEmail(dto.getProfessorEmail());
        } else{ user_professor = userService.getUserByUsername(dto.getProfessorUsername()); }
        if (user_professor==null) { return ResponseEntity.notFound().build(); }

        System.out.println(user_professor.toString());
        Student student = studentService.getStudentByUserId(user_student.getUserId());
        Professor professor = professorService.getProfessorByUserId(user_professor.getUserId());


        Review review = new Review();
        review.setReviewDateTime(LocalDateTime.now());
        review.setComment(dto.getComment());
        review.setProfessor(professor);
        review.setStudent(student);
        review.setRating(dto.getRating());

        reviewService.saveOrUpdate(review);

        return ResponseEntity.ok("REVIEW_CREATED");

    }
}
