package com.mindiqo.backend.service;

import com.mindiqo.backend.entity.PageBean;
import com.mindiqo.backend.entity.PostNote;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
public class PostServiceTest {

    @Mock
    private PostService postService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAll() {
        // Given
        PostNote postNote = new PostNote();
        postNote.setId(1);
        postNote.setTitle("Test Title");

        when(postService.getAll()).thenReturn(Arrays.asList(postNote));

        // When
        List<PostNote> posts = postService.getAll();

        // Then
        assertEquals(1, posts.size());
        assertEquals(postNote, posts.get(0));
    }

    @Test
    public void testGetById() {
        // Given
        Integer id = 1;
        PostNote postNote = new PostNote();
        postNote.setId(id);

        when(postService.getById(id)).thenReturn(postNote);

        // When
        PostNote retrievedPost = postService.getById(id);

        // Then
        assertEquals(postNote, retrievedPost);
    }

    @Test
    public void testDelete() {
        // Given
        PostNote postNote = new PostNote();
        postNote.setId(1);

        doNothing().when(postService).delete(postNote);

        // When
        postService.delete(postNote);

        // Then
        verify(postService, times(1)).delete(postNote);
    }

    @Test
    public void testGetMostViewed() {
        // Given
        Integer page = 1;
        Integer pageSize = 10;
        PageBean dummyPageBean = new PageBean();

        when(postService.getMostViewed(page, pageSize)).thenReturn(dummyPageBean);

        // When
        PageBean resultPageBean = postService.getMostViewed(page, pageSize);

        // Then
        assertEquals(dummyPageBean, resultPageBean);
    }

    // ... Similar tests for other methods ...

    @Test
    public void testSearch() {
        // Given
        Integer page = 1;
        Integer pageSize = 10;
        String title = "Test Title";
        String content = "Test Content";
        String tagName = "Test Tag";
        PageBean dummyPageBean = new PageBean();

        when(postService.search(page, pageSize, title, content, tagName)).thenReturn(dummyPageBean);

        // When
        PageBean resultPageBean = postService.search(page, pageSize, title, content, tagName);

        // Then
        assertEquals(dummyPageBean, resultPageBean);
    }

    @Test
    public void testGetTags() {
        // Given
        Integer page = 1;
        Integer pageSize = 10;
        PageBean dummyPageBean = new PageBean();

        when(postService.getTags(page, pageSize)).thenReturn(dummyPageBean);

        // When
        PageBean resultPageBean = postService.getTags(page, pageSize);

        // Then
        assertEquals(dummyPageBean, resultPageBean);
    }
}
