package com.mindiqo.backend.service.impl;

import com.mindiqo.backend.entity.Comment;
import com.mindiqo.backend.entity.Like;
import com.mindiqo.backend.entity.Notification;
import com.mindiqo.backend.mapper.NotificationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotificationServiceImpl implements com.mindiqo.backend.service.NotificationService{
    @Autowired
    NotificationMapper notificationMapper;
    @Override
    public List<Notification> getUnread(Integer userId) {
        return notificationMapper.getUnread(userId);
    }

    @Override
    public List<Notification> getRead(Integer userId) {
        return notificationMapper.getRead(userId);
    }

    @Override
    public void readANotification(Notification notification) {
        notificationMapper.readANotification(notification);
    }

    @Override
    public void deleteById(Notification notification) {
        notificationMapper.deleteById(notification);
    }

    @Override
    public Integer getUnreadCount(Integer userId) {
        return notificationMapper.getUnreadCount(userId);
    }

    @Override
    public void sendLikeNotification(Like newLike) {
        Notification notification = new Notification();
        notification.setSenderId(newLike.getUserId());
        notification.setRelatedPostId(newLike.getPostId());
        notification.setCreateTime(LocalDateTime.now());
        notification.setType(1);
        notification.setStatus(0);
        notification.setContent("liked your post");
        notificationMapper.addLikeNotification(notification);
    }

    @Override
    public void sendCommentNotification(Comment comment) {
        Notification notification = new Notification();
        notification.setSenderId(comment.getUserId());
        notification.setRelatedPostId(comment.getPostId());
        notification.setCreateTime(LocalDateTime.now());
        notification.setType(0);
        notification.setStatus(0);
        notification.setContent("commented on your post:"+"\r"+comment.getContent());
        notificationMapper.addCommentNotification(notification);
    }
}
