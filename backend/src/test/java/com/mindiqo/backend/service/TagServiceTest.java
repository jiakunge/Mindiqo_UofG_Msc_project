package com.mindiqo.backend.service;

import com.mindiqo.backend.entity.Tag;
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
public class TagServiceTest {

    @Mock
    private TagService tagService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAll() {
        // Given
        Tag tag = new Tag();
        tag.setId(1);
        tag.setTagName("Test Tag");

        when(tagService.getAll()).thenReturn(Arrays.asList(tag));

        // When
        List<Tag> tags = tagService.getAll();

        // Then
        assertEquals(1, tags.size());
        assertEquals(tag, tags.get(0));
    }

    @Test
    public void testAdd() {
        // Given
        Tag tag = new Tag();
        tag.setId(1);
        tag.setTagName("Test Tag");

        doNothing().when(tagService).add(tag);

        // When
        tagService.add(tag);

        // Then
        verify(tagService, times(1)).add(tag);
    }
}
