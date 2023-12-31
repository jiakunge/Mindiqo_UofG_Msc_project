package com.mindiqo.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Like {
    Integer id;
    Integer postId;
    Integer userId;
    LocalDateTime createTime;
}
