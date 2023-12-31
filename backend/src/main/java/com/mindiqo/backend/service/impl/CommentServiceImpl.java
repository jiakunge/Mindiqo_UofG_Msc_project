package com.mindiqo.backend.service.impl;

import com.mindiqo.backend.entity.Comment;
import com.mindiqo.backend.mapper.AuthMapper;
import com.mindiqo.backend.mapper.CommentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CommentServiceImpl  implements com.mindiqo.backend.service.CommentService{
    @Autowired
    CommentMapper commentMapper;
    @Autowired
    AuthMapper userMapper;

    @Override
    public List<Comment> getComments(Integer postId) {
        List<Comment> comments = commentMapper.getComments(postId);
        if (comments != null || comments.size() != 0) {
            for (Comment comment : comments) {
                comment.setUser(userMapper.getBasicInfoById(comment.getUserId()));
            }
        }
        return comments;
    }

    @Override
    public Integer getCommentCount(Integer id) {
        return commentMapper.getCommentCount(id);
    }

    @Override
    public void comment(Comment comment) {
        comment.setCreateTime(LocalDateTime.now());
        commentMapper.add(comment);
    }
}
