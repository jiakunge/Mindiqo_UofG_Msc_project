package com.mindiqo.backend.mapper;

import com.mindiqo.backend.entity.Notification;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface NotificationMapper {
    List<Notification> getUnread(Integer userId);

    List<Notification> getRead(Integer userId);

    void readANotification(Notification notification);

    void deleteById(Notification notification);

    Integer getUnreadCount(Integer userId);


    void addLikeNotification(Notification notification);

    void addCommentNotification(Notification notification);
}
