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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@ExtendWith(MockitoExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PostControllerTest extends BaseControllerTest{
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetPostById() throws Exception {

        mockMvc.perform(get("/api/posts/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(1))
                .andExpect(jsonPath("$.msg").value("success"));
    }

    @Test
    public void testDeletePostById() throws Exception {

        mockMvc.perform(delete("/api/posts/{id}/delete", 1)
                .header("token", JwtUtils.generateMockJwt(1)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(1))
                .andExpect(jsonPath("$.msg").value("success"));
    }

@Test
    public void testGetMostViewedPosts() throws Exception {
        mockMvc.perform(get("/api/posts/most_viewed")
                .param("page", "1")
                .param("pageSize", "6"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(1))
                .andExpect(jsonPath("$.msg").value("success"));
    }

@Test
    public void testGetMostLikedPosts() throws Exception {
        mockMvc.perform(get("/api/posts/most_liked")
                .param("page", "1")
                .param("pageSize", "6"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(1))
                .andExpect(jsonPath("$.msg").value("success"));
    }


    @Test
    public void testGetRecentPosts() throws Exception {
        mockMvc.perform(get("/api/posts/recent")
                .param("page", "1")
                .param("pageSize", "6"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(1))
                .andExpect(jsonPath("$.msg").value("success"));
    }

    @Test
    public void testSearchPosts() throws Exception {
        mockMvc.perform(get("/api/posts/search")
                .param("page", "1")
                .param("pageSize", "6")
                .param("title", "title")
                .param("content", "content")
                .param("tag", "tag"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(1))
                .andExpect(jsonPath("$.msg").value("success"));
    }

@Test
    public void testGetPopularTags() throws Exception {
        mockMvc.perform(get("/api/posts/tags")
                .param("page", "1")
                .param("pageSize", "6"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(1))
                .andExpect(jsonPath("$.msg").value("success"));
    }
}
