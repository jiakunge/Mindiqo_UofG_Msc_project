package com.mindiqo.backend.controller;

import com.mindiqo.backend.entity.Note;
import com.mindiqo.backend.entity.Tag;
import com.mindiqo.backend.service.NoteService;
import com.mindiqo.backend.service.TagService;
import com.mindiqo.backend.utils.JwtUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class NoteControllerTest extends BaseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TagService tagService;

    @MockBean
    private NoteService noteService;

    private List<Tag> mockTags;
    private List<Note> mockNotes;

    @BeforeEach
    public void setUp() {
        mockTags = Arrays.asList(
                new Tag(1, "Work"),
                new Tag(2, "Personal")
        );

        mockNotes = Arrays.asList(
                //new note
                new Note(1, "Meeting Notes", 12345, "Meeting content",
                        "Brief overview of the meeting notes.", LocalDateTime.now(), LocalDateTime.now(), mockTags, "path_to_image.jpg")
        );

    }

    @Test
    public void testGetAllTags() throws Exception {
        when(tagService.getAll()).thenReturn(mockTags);

        mockMvc.perform(get("/api/notes/tags").header("token", JwtUtils.generateMockJwt(12345)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(1))
                .andExpect(jsonPath("$.msg").value("success"))
                .andExpect(jsonPath("$.data[0].id").value(1))
                .andExpect(jsonPath("$.data[0].tagName").value("Work"));
    }

    @Test
    public void testGetAllNotes() throws Exception {
        Map<String, Object> mockNotes = new HashMap<>();
        mockNotes.put("notes", this.mockNotes);
        mockNotes.put("tags", this.mockTags);

        when(noteService.getAll(12345)).thenReturn(mockNotes);


        mockMvc.perform(get("/api/notes").header("token", JwtUtils.generateMockJwt(12345)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(1))
                .andExpect(jsonPath("$.msg").value("success"));
    }

    @Test
    public void testRenameNote() throws Exception {

        mockMvc.perform(post("/api/notes/1/rename").header("token", JwtUtils.generateMockJwt(12345))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"NewTitleForNote\"}")
                        .requestAttr("userId", 12345))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(1))
                .andExpect(jsonPath("$.msg").value("success"));
    }

    @Test
    public void testEditAbs() throws Exception {

        mockMvc.perform(post("/api/notes/1/edit_abs").header("token", JwtUtils.generateMockJwt(12345))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"abs\":\"NewAbstractForNote\"}")
                        .requestAttr("userId", 12345))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(1))
                .andExpect(jsonPath("$.msg").value("success"));
    }
    @Test
    public void testChangeImg() throws Exception{

        mockMvc.perform(post("/api/notes/1/change_img").header("token", JwtUtils.generateMockJwt(12345))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"img\":\"NewImageForNote\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(1))
                .andExpect(jsonPath("$.msg").value("success"));
    }
    @Test
    public void testDeleteNote() throws Exception{
        doNothing().when(noteService).deleteById(1, 12345);
        mockMvc.perform(delete("/api/notes/1/delete").header("token", JwtUtils.generateMockJwt(12345)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(1))
                .andExpect(jsonPath("$.msg").value("success"));
    }
    @Test
    public void testAddNote() throws Exception{

        doNothing().when(noteService);
        mockMvc.perform(post("/api/notes/add").header("token", JwtUtils.generateMockJwt(12345))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"NewTitleForNote\",\"abs\":\"NewAbstractForNote\"}"))
                .andExpect(status().isOk());
    }

}
