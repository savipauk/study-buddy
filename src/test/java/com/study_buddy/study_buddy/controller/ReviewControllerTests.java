package com.study_buddy.study_buddy.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.study_buddy.study_buddy.dto.ReviewDto;
import com.study_buddy.study_buddy.model.*;
import com.study_buddy.study_buddy.service.ProfessorService;
import com.study_buddy.study_buddy.service.ReviewService;
import com.study_buddy.study_buddy.service.StudentService;
import com.study_buddy.study_buddy.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(controllers = ReviewController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class ReviewControllerTests {
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReviewService reviewService;

    @MockBean
    private UserService userService;

    @MockBean
    private StudentService studentService;

    @MockBean
    private ProfessorService professorService;


    private Review review;
    private Student student;
    private Professor professor;
    private ReviewDto reviewDto;

    /*reviewDto.setStudentUsername(user.getUsername());
        reviewDto.setProfessorUsername(user.getUsername());*/
    
    @BeforeEach
    public void init(){
        student = new Student();
        student.setStudentId(1L);

        professor = new Professor();
        professor.setProfessorId(1L);

        review = new Review();
        review.setStudent(student);
        review.setProfessor(professor);
        review.setRating(5);
        review.setComment("The professor explained the concepts very clearly. Highly recommend!");
        review.setReviewDateTime(LocalDateTime.now());

        reviewDto = new ReviewDto();
        reviewDto.setRating(review.getRating());
        reviewDto.setComment(review.getComment());
        reviewDto.setReviewDateTime(review.getReviewDateTime());
        reviewDto.setReviewId(review.getReviewId());

    }

    @Test
    public void ReviewController_GetAllReviews_ReturnReviewDto() throws Exception {
        given(reviewService.getAllReviews()).willReturn(Arrays.asList(review));
        given(reviewService.convertToDto(any(Review.class))).willReturn(reviewDto);

        ResultActions response = mockMvc.perform(get("/reviews/allReviews")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].rating").value(reviewDto.getRating()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].comment").value(reviewDto.getComment()));;
    }
}
