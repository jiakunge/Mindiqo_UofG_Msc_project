package com.mindiqo.backend.controller;

import com.mindiqo.backend.entity.User;
import com.mindiqo.backend.service.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class AuthControllerTest extends BaseControllerTest{

    private MockMvc mockMvc;

    @Mock
    private AuthService authService;

    @InjectMocks
    private AuthController authController;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
    }

    @Test
    public void testLogin_Success() throws Exception {
        User testUser = new User();
        testUser.setUsername("exampleUser");
        testUser.setPassword("examplePassword");
        testUser.setId(123);
        testUser.setAvatar("exampleAvatar");
        when(authService.login(any(User.class))).thenReturn(testUser);

        mockMvc.perform(post("/api/login")
                        .contentType("application/json")
                        .content("{\"username\": \"exampleUser\", \"password\": \"examplePassword\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(1))
                .andExpect(jsonPath("$.msg").value("success"));
    }

    @Test
    public void testLogin_Failure() throws Exception {
        when(authService.login(any(User.class))).thenReturn(null);

        mockMvc.perform(post("/api/login")
                        .contentType("application/json")
                        .content("{\"username\": \"exampleUser\", \"password\": \"examplePassword\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(jsonPath("$.msg").value("invalid username and password"));
    }

    @Test
    public void testRegister_UsernameExists() throws Exception {
        when(authService.register(any(User.class))).thenReturn(false);

        mockMvc.perform(post("/api/register")
                        .contentType("application/json")
                        .content("{\"username\": \"exampleUser\", \"password\": \"examplePassword\", \"email\": \"example@email.com\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(jsonPath("$.msg").value("username already exists"));
    }

    @Test
    public void testRegister_Success() throws Exception {
        when(authService.register(any(User.class))).thenReturn(true);

        mockMvc.perform(post("/api/register")
                        .contentType("application/json")
                        .content("{\"username\": \"exampleUser\", \"password\": \"examplePassword\", \"email\": \"example@email.com\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(1))
                .andExpect(jsonPath("$.msg").value("success"));
    }

    @Test
    public void testResetPassword() throws Exception {
        doNothing().when(authService).resetPassword(any(User.class));

        mockMvc.perform(post("/api/reset_password")
                        .contentType("application/json")
                        .content("{\"username\": \"exampleUser\", \"password\": \"newPassword\", \"id\": 123456}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(1))
                .andExpect(jsonPath("$.msg").value("success"));
    }
}
