package com.mindiqo.backend.service;

import com.mindiqo.backend.entity.Like;

public interface LikeService {
    void like(Like like);

    void unlike(Like like);

    Integer getLikes(Integer id);

    Boolean isLiked(Like newLike);
}
