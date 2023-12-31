package com.mindiqo.backend.mapper;

import com.mindiqo.backend.entity.PostNote;
import com.mindiqo.backend.entity.Tag;
import com.mindiqo.backend.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PostMapper {
    public List<PostNote> getAll();

    public PostNote getById(Integer id);

    public void add(PostNote postNote);

    public void addTagToPostById(Integer postNoteId, Tag tag);

    List<PostNote> getUserPosts(User user);

    void delete(PostNote deletePost);

    void incrementViewCount(Integer id);

    List<PostNote> getPostsOrderedByView();

    List<PostNote> getPostsOrderedByLiked();

    List<PostNote> getPostsOrderedByCreateTime();

    List<PostNote> search(String title, String content, String tagName);

    List<Tag> getTags();
}
