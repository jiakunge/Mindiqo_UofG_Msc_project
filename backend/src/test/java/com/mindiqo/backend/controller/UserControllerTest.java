package com.mindiqo.backend.controller;

import com.mindiqo.backend.utils.JwtUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@ExtendWith(MockitoExtension.class)
@ExtendWith(MockitoExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest extends BaseControllerTest{
    @Autowired
    private MockMvc mockMvc;


    @Test
    public void testGetUserByUsername() throws Exception {
        mockMvc.perform(get("/api/user/{username}", "test")
                        .header("token", JwtUtils.generateMockJwt(1)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(1))
                .andExpect(jsonPath("$.msg").value("success"));
    }

    @Test
    public void testGetUsersPosts() throws Exception{
        mockMvc.perform(post("/api/user/post")
                        .header("token", JwtUtils.generateMockJwt(1))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"user\":{\"id\":1}}"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(1))
                .andExpect(jsonPath("$.msg").value("success"));

    }

    @Test
    public void testUpdateUserInfo() throws Exception{
        mockMvc.perform(post("/api/user/update")
                .header("token", JwtUtils.generateMockJwt(1))
                        .contentType(MediaType.APPLICATION_JSON)

                        .content("{\"id\":1,\"username\":\"test\",\"avatar\":\"https://sample.com/newavatar.jpg\",\"bio\":\"Updated bio text\"}"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(1))
                .andExpect(jsonPath("$.msg").value("success"));

    }
}
