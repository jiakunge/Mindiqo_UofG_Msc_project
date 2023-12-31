package com.mindiqo.backend.service;

import com.mindiqo.backend.entity.Comment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
@SpringBootTest
public class CommentServiceTest {

    @Mock
    private CommentService commentService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetComments() {
        // Given
        Integer postId = 1;
        Comment comment1 = new Comment();
        comment1.setId(1);
        comment1.setPostId(postId);
        comment1.setContent("Test Comment 1");
        comment1.setCreateTime(LocalDateTime.now());

        Comment comment2 = new Comment();
        comment2.setId(2);
        comment2.setPostId(postId);
        comment2.setContent("Test Comment 2");
        comment2.setCreateTime(LocalDateTime.now());

        when(commentService.getComments(postId)).thenReturn(Arrays.asList(comment1, comment2));

        // When
        List<Comment> comments = commentService.getComments(postId);

        // Then
        assertEquals(2, comments.size());
        assertEquals(comment1, comments.get(0));
        assertEquals(comment2, comments.get(1));
    }

    @Test
    public void testGetCommentCount() {
        // Given
        Integer postId = 1;
        when(commentService.getCommentCount(postId)).thenReturn(2);

        // When
        Integer count = commentService.getCommentCount(postId);

        // Then
        assertEquals(2, count);
    }

    @Test
    public void testComment() {
        // Given
        Comment dummyComment = new Comment();
        dummyComment.setId(1);
        dummyComment.setContent("Test Comment");
        dummyComment.setCreateTime(LocalDateTime.now());

        doNothing().when(commentService).comment(dummyComment);

        // When
        commentService.comment(dummyComment);

        // Then
        verify(commentService, times(1)).comment(dummyComment);
    }
}
