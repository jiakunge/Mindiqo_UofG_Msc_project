package com.mindiqo.backend.service;

import com.mindiqo.backend.entity.Comment;
import com.mindiqo.backend.entity.Like;
import com.mindiqo.backend.entity.Notification;

import java.util.List;

public interface NotificationService {
    List<Notification> getUnread(Integer userId);

    List<Notification> getRead(Integer userId);

    void readANotification(Notification notification);

    void deleteById(Notification notification);

    Integer getUnreadCount(Integer userId);

    void sendLikeNotification(Like newLike);

    void sendCommentNotification(Comment comment);
}
