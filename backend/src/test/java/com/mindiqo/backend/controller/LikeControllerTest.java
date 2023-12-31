package com.mindiqo.backend.controller;

import com.mindiqo.backend.entity.Like;
import com.mindiqo.backend.service.LikeService;
import com.mindiqo.backend.service.NotificationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class LikeControllerTest extends BaseControllerTest {
    private MockMvc mockMvc;
    @Mock
    private LikeService likeService;

    @Mock
    private NotificationService notificationService;

    @InjectMocks
    private LikeController likeController;



    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(likeController).build();
    }

    @Test
    void testLike() throws Exception {
        mockMvc.perform(post("/api/posts/1/like").requestAttr("userId", 100))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(1))
                .andExpect(jsonPath("$.msg").value("success"));

        verify(likeService, times(1)).like(any(Like.class));
        verify(notificationService, times(1)).sendLikeNotification(any(Like.class));
    }

    @Test
    void testUnlike() throws Exception {
        mockMvc.perform(delete("/api/posts/1/unlike").requestAttr("userId", 100))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(1))
                .andExpect(jsonPath("$.msg").value("success"));

        verify(likeService, times(1)).unlike(any(Like.class));
    }

    @Test
    void testGetLikes() throws Exception {
        when(likeService.getLikes(1)).thenReturn(32);

        mockMvc.perform(get("/api/posts/1/likes"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(1))
                .andExpect(jsonPath("$.msg").value("success"))
                .andExpect(jsonPath("$.data").value(32));
    }

    @Test
    void testIsLiked() throws Exception {
        when(likeService.isLiked(any(Like.class))).thenReturn(true);

        mockMvc.perform(get("/api/posts/1/is_liked").requestAttr("userId", 100))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(1))
                .andExpect(jsonPath("$.msg").value("success"))
                .andExpect(jsonPath("$.data").value(true));
    }
}
