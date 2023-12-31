package com.mindiqo.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostNote implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;
    private Integer noteId;
    private Integer userId;
    private String title;
    private String abs;
    private String content;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private String img;
    private User creator;
    private List<Tag> tags;
    private Integer likeCount;
    private Integer commentCount;
    private Integer viewCount;
}
