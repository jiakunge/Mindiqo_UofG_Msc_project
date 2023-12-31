package com.mindiqo.backend.mapper;

import com.mindiqo.backend.entity.Like;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LikeMapper {
    void add(Like like);

    void delete(Like like);

    Integer getLikes(Integer id);

    Like getLike(Like like);
}
