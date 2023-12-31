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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class NotificationControllerTest extends BaseControllerTest{

    @Autowired
    private MockMvc mockMvc;


    @Test
    public void testGetUnreadNotifications() throws Exception {
        Integer mockUserId = 1;
        String mockToken = JwtUtils.generateMockJwt(mockUserId);

        mockMvc.perform(get("/api/notifications/unread")
                        .header("token", mockToken))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(1))
                .andExpect(jsonPath("$.msg").value("success"));

    }

    @Test
    public void testGetReadNotifications() throws Exception {
        Integer mockUserId = 1;
        String mockToken = JwtUtils.generateMockJwt(mockUserId);

        mockMvc.perform(get("/api/notifications/read")
                        .header("token", mockToken))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(1))
                .andExpect(jsonPath("$.msg").value("success"));

    }

    @Test
    public void testMarkANotificationAsRead() throws Exception {
        Integer mockUserId = 1;
        String mockToken = JwtUtils.generateMockJwt(mockUserId);

        mockMvc.perform(post("/api/notifications/read")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"notification\":{\"id\":1}}")
                        .header("token", mockToken))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(1))
                .andExpect(jsonPath("$.msg").value("success"));

    }

    @Test
    public void testDeleteANotification() throws Exception {
        Integer mockUserId = 1;
        String mockToken = JwtUtils.generateMockJwt(mockUserId);

        mockMvc.perform(delete("/api/notifications/1")
                        .header("token", mockToken))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(1))
                .andExpect(jsonPath("$.msg").value("success"));

    }

    @Test
    public void testGetUnreadNotificationsCount() throws Exception {
        Integer mockUserId = 1;
        String mockToken = JwtUtils.generateMockJwt(mockUserId);

        mockMvc.perform(get("/api/notifications/unread_count")
                        .header("token", mockToken))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(1))
                .andExpect(jsonPath("$.msg").value("success"));
    }


}
