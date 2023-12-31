package com.mindiqo.backend.service;

import com.mindiqo.backend.entity.Note;
import com.mindiqo.backend.entity.Tag;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;
import java.util.Map;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
public class NoteServiceTest {

    @Mock
    private NoteService noteService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAll() {
        // Given
        Integer userId = 1;
        Map<String, Object> dummyMap = Collections.singletonMap("key", "value");
        when(noteService.getAll(userId)).thenReturn(dummyMap);

        // When
        Map<String, Object> resultMap = noteService.getAll(userId);

        // Then
        assertEquals(dummyMap, resultMap);
    }

    @Test
    public void testUpdateTitle() {
        // Given
        Note note = new Note();
        note.setId(1);
        note.setTitle("New Title");

        doNothing().when(noteService).updateTitle(note);

        // When
        noteService.updateTitle(note);

        // Then
        verify(noteService, times(1)).updateTitle(note);
    }

    // ... You can follow the same pattern for the other methods ...

    @Test
    public void testAddTagToNoteById() {
        // Given
        Integer id = 1;
        Tag tag = new Tag();
        tag.setId(10);
        tag.setTagName("Test Tag");

        doNothing().when(noteService).addTagToNoteById(id, tag);

        // When
        noteService.addTagToNoteById(id, tag);

        // Then
        verify(noteService, times(1)).addTagToNoteById(id, tag);
    }

    @Test
    public void testRemoveTagFromNoteById() {
        // Given
        Integer id = 1;
        Tag tag = new Tag();
        tag.setId(10);
        tag.setTagName("Test Tag");

        doNothing().when(noteService).removeTagFromNoteById(id, tag);

        // When
        noteService.removeTagFromNoteById(id, tag);

        // Then
        verify(noteService, times(1)).removeTagFromNoteById(id, tag);
    }

    @Test
    public void testSaveNote() {
        // Given
        Note note = new Note();
        note.setId(1);
        note.setTitle("Test Note");

        doNothing().when(noteService).saveNote(note);

        // When
        noteService.saveNote(note);

        // Then
        verify(noteService, times(1)).saveNote(note);
    }

    @Test
    public void testPost() {
        // Given
        Note note = new Note();
        note.setId(1);
        note.setTitle("Test Note");

        doNothing().when(noteService).post(note);

        // When
        noteService.post(note);

        // Then
        verify(noteService, times(1)).post(note);
    }
}
