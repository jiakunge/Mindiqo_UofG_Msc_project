package com.mindiqo.backend.service;

import com.mindiqo.backend.entity.PageBean;
import com.mindiqo.backend.entity.PostNote;

import java.util.List;

public interface PostService {
    List<PostNote> getAll();

    PostNote getById(Integer id);

    void delete(PostNote deletePost);

    PageBean getMostViewed(Integer page, Integer pageSize);

    PageBean getMostLiked(Integer page, Integer pageSize);

    PageBean getRecent(Integer page, Integer pageSize);

    PageBean search(Integer page, Integer pageSize, String title, String content, String tagName);

    PageBean getTags(Integer page, Integer pageSize);
}
