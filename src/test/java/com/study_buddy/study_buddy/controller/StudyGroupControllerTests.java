package com.study_buddy.study_buddy.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.study_buddy.study_buddy.dto.StudyGroupDto;
import com.study_buddy.study_buddy.model.*;
import com.study_buddy.study_buddy.service.GroupMemberService;
import com.study_buddy.study_buddy.service.StudentService;
import com.study_buddy.study_buddy.service.StudyGroupService;
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

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;


@WebMvcTest(controllers = StudyGroupController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class StudyGroupControllerTests {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudyGroupService studyGroupService;

    @MockBean
    private StudentService studentService;

    @MockBean
    private UserService userService;

    @MockBean
    private GroupMemberService groupMemberService;


    private StudyGroup studyGroup;
    private StudyGroupDto studyGroupDto;
    private User user;
    private Student student;

    @BeforeEach
    public void init(){
        user = new User();
        user.setUserId(1L);
        user.setEmail("test@example.com");
        user.setPassword("password123");
        user.setUsername("janedoe");
        user.setFirstName("Jane");
        user.setLastName("Doe");
        user.setDescription("Math lover!");
        user.setRole(StudyRole.STUDENT);
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        user.setDateOfBirth(LocalDate.of(2003,1,1));
        user.setGender(Gender.F);
        user.setCity("New York");
        user.setOauthProvider("");
        user.setOauthId("");

        student = new Student();
        student.setUser(user);
        student.setStudentId(1L);

        studyGroup = new StudyGroup();
        studyGroup.setGroupId(1L);
        studyGroup.setGroupName("testGroup");
        studyGroup.setLocation("New Zeland");
        studyGroup.setMaxMembers(10);
        studyGroup.setDate(LocalDate.now());
        studyGroup.setExpirationDate(LocalDate.now().minusDays(2));
        studyGroup.setCreator(student);

        studyGroupDto = new StudyGroupDto();
        studyGroupDto.setStudyGroupId(1L);
        studyGroupDto.setGroupName("testGroup");
        studyGroupDto.setLocation("New Zeland");
        studyGroupDto.setMaxMembers(10);
        studyGroupDto.setDate(LocalDate.now());
        studyGroupDto.setExpirationDate(LocalDate.now().minusDays(2));
        studyGroupDto.setUsername(user.getUsername());
        studyGroupDto.setEmail(user.getEmail());
    }

    @Test
    public void StudyGroupController_CreateStudyGroup_ReturnStudyGroupDto() throws Exception {
        given(userService.getUserByEmail(user.getEmail())).willReturn(user);
        given(studentService.getStudentByUserId(1L)).willReturn(student);
        given(studyGroupService.createStudyGroup(studyGroup)).willReturn(studyGroup);

        ResultActions response = mockMvc.perform(post("/studyGroup/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(studyGroupDto)));

        response.andExpect(MockMvcResultMatchers.status().isCreated());
    }
}
