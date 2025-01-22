package com.study_buddy.study_buddy.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.study_buddy.study_buddy.dto.Login;
import com.study_buddy.study_buddy.model.Gender;
import com.study_buddy.study_buddy.model.StudyRole;
import com.study_buddy.study_buddy.model.User;
import com.study_buddy.study_buddy.service.OAuthService;
import com.study_buddy.study_buddy.service.ProfessorService;
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

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(controllers = LoginController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class LoginControllerTests {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private OAuthService oAuthService;

    @MockBean
    private StudentService studentService;

    @MockBean
    private ProfessorService professorService;

    private User user;
    private Login login;
    private Login nonExistingUsername;
    private Login wrongPassword;

    @BeforeEach
    public void init() {
        user = new User();
        user.setUserId(1L);
        user.setEmail("test@example.com");
        user.setPassword("password123");
        user.setUsername("$2a$10$BD1piSn8s8QgTo6lqegAJurHPkI4H6psG12L1JrKUJz6KYYfiXDue");
        user.setFirstName("Jane");
        user.setLastName("Doe");
        user.setDescription("Math lover!");
        user.setRole(StudyRole.STUDENT);
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        user.setDateOfBirth(LocalDate.of(2003, 1, 1));
        user.setGender(Gender.F);
        user.setCity("New York");
        user.setOauthProvider("");
        user.setOauthId("");

        login = new Login();
        login.setUsername("janedoe");
        login.setPassword("password123");

        nonExistingUsername = new Login();
        nonExistingUsername.setUsername("IdontExist");
        nonExistingUsername.setPassword("password123");

        wrongPassword = new Login();
        wrongPassword.setUsername("janedoe");
        wrongPassword.setPassword("wrongPassword");
    }

    @Test
    public void LoginController_LoginExistingUser_ReturnResponse() throws Exception {
        given(userService.userExistsByUsername(login.getUsername())).willReturn(true);
        given(userService.getUserByUsername(login.getUsername())).willReturn(user);
        given(userService.verifyPassword(user, login.getPassword())).willReturn(true);


        ResultActions response = mockMvc.perform(post("/login/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(login)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.studyRole").value(StudyRole.STUDENT.getValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Successful login"));
    }

    @Test
    public void LoginController_LoginNonExistingUser_ReturnResponse() throws Exception {
        given(userService.userExistsByUsername(nonExistingUsername.getUsername())).willReturn(false);

        ResultActions response = mockMvc.perform(post("/login/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(nonExistingUsername)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("User doesn't exist"));
    }

    @Test
    public void LoginController_LoginWithWrongPassword_ReturnResponse() throws Exception {
        given(userService.userExistsByUsername(wrongPassword.getUsername())).willReturn(true);
        given(userService.getUserByUsername(wrongPassword.getUsername())).willReturn(user);
        given(userService.verifyPassword(user, wrongPassword.getPassword())).willReturn(false);


        ResultActions response = mockMvc.perform(post("/login/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(login)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.passwordCheck").value("NOT_OK"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Wrong password"));
    }
}
