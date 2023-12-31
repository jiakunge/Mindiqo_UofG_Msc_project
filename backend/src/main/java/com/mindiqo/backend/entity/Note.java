package com.mindiqo.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Note {
    Integer id;
    String title;
    Integer creatorId;
    String content;
    String abs; // abstract
    LocalDateTime createTime;
    LocalDateTime updateTime;
    List<Tag> tags;
    String img;
}
