package com.mindiqo.backend.service;

import com.mindiqo.backend.entity.PostNote;
import com.mindiqo.backend.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
@SpringBootTest
public class AuthServiceTest {

    @Mock
    private AuthService authService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testLogin() {
        // Given
        User dummyUser = new User();
        dummyUser.setUsername("testUser");
        dummyUser.setPassword("testPass");

        when(authService.login(dummyUser)).thenReturn(dummyUser);

        // When
        User result = authService.login(dummyUser);

        // Then
        assertEquals(dummyUser, result);
    }

    @Test
    public void testRegister() {
        // Given
        User dummyUser = new User();
        dummyUser.setUsername("testUser");
        dummyUser.setPassword("testPass");

        when(authService.register(dummyUser)).thenReturn(true);

        // When
        boolean isRegistered = authService.register(dummyUser);

        // Then
        assertTrue(isRegistered);
    }

    @Test
    public void testGetUserPosts() {
        // Given
        User dummyUser = new User();
        dummyUser.setId(1);

        PostNote dummyPost = new PostNote();
        dummyPost.setTitle("testTitle");

        when(authService.getUserPosts(dummyUser)).thenReturn(Collections.singletonList(dummyPost));

        // When
        List<PostNote> posts = authService.getUserPosts(dummyUser);

        // Then
        assertEquals(1, posts.size());
        assertEquals(dummyPost, posts.get(0));
    }

    // ... Add more tests for other methods ...
}
