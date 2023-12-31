package com.mindiqo.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Notification {
    private Integer id;
    private Integer senderId;
    private Integer receiverId;
    private String content;
    private Integer type; // 0: comment, 1: like
    private  Integer relatedPostId;
    private Integer status; // 0: unread, 1: read
    private LocalDateTime createTime;
    private User sender;
}
