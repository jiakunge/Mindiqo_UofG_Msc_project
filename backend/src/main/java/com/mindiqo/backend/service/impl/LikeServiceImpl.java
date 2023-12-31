package com.mindiqo.backend.service.impl;

import com.mindiqo.backend.entity.Like;
import com.mindiqo.backend.mapper.LikeMapper;
import com.mindiqo.backend.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class LikeServiceImpl implements LikeService {
    @Autowired
    LikeMapper likeMapper;

    @Override
    public void like(Like like) {
        like.setCreateTime(LocalDateTime.now());
        likeMapper.add(like);
    }

    @Override
    public void unlike(Like like) {
        likeMapper.delete(like);
    }

    @Override
    public Integer getLikes(Integer id) {

        return likeMapper.getLikes(id);
    }

    @Override
    public Boolean isLiked(Like like) {
        return likeMapper.getLike(like) != null;
    }

}
