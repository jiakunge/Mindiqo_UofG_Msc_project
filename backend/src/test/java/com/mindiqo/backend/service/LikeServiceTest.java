package com.mindiqo.backend.service;

import com.mindiqo.backend.entity.Like;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class LikeServiceTest {

    @Mock
    private LikeService likeService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testLike() {
        // Given
        Like dummyLike = new Like();
        dummyLike.setId(1);
        dummyLike.setPostId(10);
        dummyLike.setUserId(100);

        doNothing().when(likeService).like(dummyLike);

        // When
        likeService.like(dummyLike);

        // Then
        verify(likeService, times(1)).like(dummyLike);
    }

    @Test
    public void testUnlike() {
        // Given
        Like dummyLike = new Like();
        dummyLike.setId(1);
        dummyLike.setPostId(10);
        dummyLike.setUserId(100);

        doNothing().when(likeService).unlike(dummyLike);

        // When
        likeService.unlike(dummyLike);

        // Then
        verify(likeService, times(1)).unlike(dummyLike);
    }

    @Test
    public void testGetLikes() {
        // Given
        Integer postId = 10;
        when(likeService.getLikes(postId)).thenReturn(5);

        // When
        Integer count = likeService.getLikes(postId);

        // Then
        assertEquals(5, count);
    }

    @Test
    public void testIsLiked() {
        // Given
        Like newLike = new Like();
        newLike.setPostId(10);
        newLike.setUserId(100);

        when(likeService.isLiked(newLike)).thenReturn(true);

        // When
        Boolean isLiked = likeService.isLiked(newLike);

        // Then
        assertTrue(isLiked);
    }
}
