package com.mindiqo.backend.mapper;

import com.mindiqo.backend.entity.Note;
import com.mindiqo.backend.entity.Tag;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface NoteMapper {

    public List<Note> getAll(Integer userId);

    public void updateTitleById(Note note);

    public void updateAbsById(Note note);

    public void updateImgById(Note note);
    public void deleteById(Integer id, Integer userId);

    public void add(Note note);

    public Note getById(Integer id, Integer userId);

    public void addTagToNoteById(Integer noteId, Tag tag);

    public void removeTagFromNoteById(Integer noteId, Tag tag);

    public void updateContentById(Note note);


}
