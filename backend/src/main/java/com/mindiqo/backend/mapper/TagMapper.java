package com.mindiqo.backend.mapper;

import com.mindiqo.backend.entity.Note;
import com.mindiqo.backend.entity.PostNote;
import com.mindiqo.backend.entity.Tag;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TagMapper {
    public List<Tag> getAll();

    public List<Tag> getByNote(Note note);


    public void insert(Tag tag);

    public List<Tag> getTagsByPost(PostNote postNote);
}
