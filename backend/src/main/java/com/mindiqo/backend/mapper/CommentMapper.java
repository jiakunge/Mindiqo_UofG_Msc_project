package com.mindiqo.backend.mapper;

import com.mindiqo.backend.entity.Comment;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommentMapper {
    List<Comment> getComments(Integer postId);
    Integer getCommentCount(Integer postId);

    void add(Comment comment);
}
