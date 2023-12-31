package com.mindiqo.backend.controller;

import com.mindiqo.backend.entity.Comment;
import com.mindiqo.backend.entity.User;
import com.mindiqo.backend.service.CommentService;
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

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class CommentControllerTest extends BaseControllerTest{

    private MockMvc mockMvc;

    @InjectMocks
    private CommentController commentController;

    @Mock
    private CommentService commentService;

    @Mock
    private NotificationService notificationService;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(commentController).build();
    }

    @Test
    public void testGetComments() throws Exception {
        Comment mockComment = new Comment();
        mockComment.setId(123);
        mockComment.setPostId(456);
        mockComment.setUserId(789);
        mockComment.setContent("This is a comment.");

        User user = new User();
        user.setId(789);
        user.setUsername("JohnDoe");
        user.setAvatar("link_to_avatar.jpg");

        mockComment.setUser(user);

        List<Comment> comments = Arrays.asList(mockComment);

        when(commentService.getComments(456)).thenReturn(comments);

        mockMvc.perform(get("/api/posts/456/comments"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(1))
                .andExpect(jsonPath("$.msg").value("success"))
                .andExpect(jsonPath("$.data[0].id").value(123));

        verify(commentService).getComments(456);
    }

    @Test
    public void testComment() throws Exception {
        Comment comment = new Comment();
        comment.setPostId(456);
        comment.setContent("This is a comment.");
        comment.setUserId(789);


        mockMvc.perform(post("/api/posts/456/comment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"content\":\"This is a comment.\",\"postId\":456}")
                        .requestAttr("userId", 789))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(1))
                .andExpect(jsonPath("$.msg").value("success"));

        verify(commentService).comment(comment);
        verify(notificationService).sendCommentNotification(comment);
    }
}
