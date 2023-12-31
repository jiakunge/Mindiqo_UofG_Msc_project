package com.mindiqo.backend.service;

import com.mindiqo.backend.entity.Note;
import com.mindiqo.backend.entity.Tag;

import java.util.Map;

public interface NoteService {

    Map<String, Object> getAll(Integer userId);
    void updateTitle(Note note);
    void updateAbs(Note note);
    void updateImg(Note note);
    void deleteById(Integer id, Integer userId);
    Note add(Note note);
    Note getById(Integer id, Integer userId);

    void addTagToNoteById(Integer id, Tag tag);

    void removeTagFromNoteById(Integer id, Tag tag);

    void saveNote(Note note);


    void post(Note note);
}
