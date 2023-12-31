package com.mindiqo.backend.service.impl;

import com.mindiqo.backend.entity.Note;
import com.mindiqo.backend.entity.PostNote;
import com.mindiqo.backend.entity.Tag;
import com.mindiqo.backend.mapper.NoteMapper;
import com.mindiqo.backend.mapper.PostMapper;
import com.mindiqo.backend.mapper.TagMapper;
import com.mindiqo.backend.service.NoteService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class NoteServiceImpl implements NoteService {
    @Autowired
    NoteMapper noteMapper;
    @Autowired
    TagMapper tagMapper;

    @Autowired
    PostMapper postMapper;

    @Override
    public Map<String, Object> getAll(Integer userId) {
        Set<Tag> tags = new HashSet<>();
        List<Note> notes = noteMapper.getAll(userId);
        for (Note note : notes) {
            note.setTags(tagMapper.getByNote(note));
            tags.addAll(note.getTags());
        }
        Map<String, Object> response = new HashMap<>();
        response.put("notes", notes);
        response.put("tags", new ArrayList<>(tags));
        return response;
    }

    @Override
    public void updateTitle(Note note) {
        note.setUpdateTime(LocalDateTime.now());
        noteMapper.updateTitleById(note);
    }

    @Override
    public void updateAbs(Note note) {
        note.setUpdateTime(LocalDateTime.now());
        noteMapper.updateAbsById(note);
    }

    @Override
    public void updateImg(Note note) {
        note.setUpdateTime(LocalDateTime.now());
        noteMapper.updateImgById(note);
    }

    @Override
    public void deleteById(Integer id, Integer userId) {
        noteMapper.deleteById(id, userId);
    }

    @Override
    public Note add(Note note) {
        //if note.title is null or "", set it to "Untitled"
        if (note.getTitle() == null || note.getTitle().equals("")) {
            note.setTitle("Untitled");
        }
        //if note.abs is null or "", set it to "No abstract"
        if (note.getAbs() == null || note.getAbs().equals("")) {
            note.setAbs("No abstract");
        }

        note.setCreateTime(LocalDateTime.now());
        note.setUpdateTime(LocalDateTime.now());

        noteMapper.add(note);
        return note;
    }

    @Override
    public Note getById(Integer id, Integer userId) {
        Note note = noteMapper.getById(id, userId);
        note.setTags(tagMapper.getByNote(note));
        return note;
    }

    @Override
    public void addTagToNoteById(Integer NoteId, Tag tag) {
        noteMapper.addTagToNoteById(NoteId, tag);
    }

    @Override
    public void removeTagFromNoteById(Integer noteId, Tag tag) {
        noteMapper.removeTagFromNoteById(noteId, tag);
    }

    @Override
    public void saveNote(Note note) {
        note.setUpdateTime(LocalDateTime.now());
        noteMapper.updateContentById(note);
    }

    @Override
    public void post(Note note) {
        Note publicNote = this.getById(note.getId(), note.getCreatorId());

        PostNote postNote = new PostNote();
        postNote.setUserId(publicNote.getCreatorId());
        postNote.setNoteId(publicNote.getId());
        postNote.setTitle(publicNote.getTitle());
        //generate abstract
        if (publicNote.getAbs() == null || publicNote.getAbs().equals("No abstract") || publicNote.getAbs().equals("")) {
            String htmlContent = publicNote.getContent();
            Document doc = Jsoup.parse(htmlContent);
            String textContent = doc.text(); //get text only from html
            String abs;
            if (textContent.length() > 100) {
                abs = textContent.substring(0, 100);
            } else {
                abs = textContent;
            }
            publicNote.setAbs(abs);
        } else {
            publicNote.setAbs(publicNote.getAbs());
        }
        postNote.setAbs(publicNote.getAbs());
        postNote.setImg(publicNote.getImg());
        postNote.setCreateTime(LocalDateTime.now());
        postNote.setUpdateTime(LocalDateTime.now());
        postNote.setContent(publicNote.getContent());
        if (publicNote.getTags() != null) {
            postNote.setTags(publicNote.getTags());
            postMapper.add(postNote);
            List<Tag> tags = postNote.getTags();
            for (Tag tag : tags) {
                postMapper.addTagToPostById(postNote.getId(), tag);
            }
        }


    }


}
