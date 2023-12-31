package com.mindiqo.backend.service;


import com.mindiqo.backend.entity.Comment;

import java.util.List;

public interface CommentService {
    List<Comment> getComments(Integer postId);

    Integer getCommentCount(Integer id);

    void comment(Comment comment);
}
