package com.mindiqo.backend.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.mindiqo.backend.entity.PageBean;
import com.mindiqo.backend.entity.PostNote;
import com.mindiqo.backend.entity.Tag;
import com.mindiqo.backend.mapper.PostMapper;
import com.mindiqo.backend.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class PostServiceImpl implements PostService {
    @Autowired
    PostMapper postMapper;

    @Autowired
    private RedisTemplate redisTemplate;



    @Override
    public List<PostNote> getAll() {
        String key = "posts";
        List<PostNote> cachedPosts = (List<PostNote>) redisTemplate.opsForValue().get(key);
        if (cachedPosts != null) {
            return cachedPosts;
        }
        List<PostNote> postNotes = postMapper.getPostsOrderedByView();
        redisTemplate.opsForValue().set(key, postNotes, 10, TimeUnit.MINUTES); // cache the result
        return postNotes;
    }

    @Override
    public PostNote getById(Integer id) {
        String key = "post:" + id;
        PostNote cachedPost = (PostNote) redisTemplate.opsForValue().get(key);
        if (cachedPost != null) {
            return cachedPost;
        }
        postMapper.incrementViewCount(id);// increment view count
        PostNote postNote = postMapper.getById(id);
        redisTemplate.opsForValue().set(key, postNote, 10, TimeUnit.MINUTES); // cache the result
        return postNote;
    }

    @Override
    public void delete(PostNote deletePost) {
        postMapper.delete(deletePost);
    }

    @Override
    public PageBean getMostViewed(Integer page, Integer pageSize) {
        String key = "most_viewed:" + page + ":" + pageSize;
        PageBean cachedPage = (PageBean) redisTemplate.opsForValue().get(key);
        if (cachedPage != null) {
            return cachedPage;
        }

        PageHelper.startPage(page, pageSize);
        List<PostNote> postNotes = postMapper.getPostsOrderedByView();
        Page<PostNote> posts = (Page<PostNote>) postNotes;
        PageBean pageBean = new PageBean(posts.getTotal(), posts.getResult());
        redisTemplate.opsForValue().set(key,pageBean,10, TimeUnit.MINUTES);  // cache the result

        return pageBean;
    }

    @Override
    public PageBean getMostLiked(Integer page, Integer pageSize) {
        String key = "most_liked:" + page + ":" + pageSize;
        PageBean cachedPage = (PageBean) redisTemplate.opsForValue().get(key);
        if (cachedPage != null) {
            return cachedPage;
        }

        PageHelper.startPage(page, pageSize);
        List<PostNote> postNotes = postMapper.getPostsOrderedByLiked();
        Page<PostNote> posts = (Page<PostNote>) postNotes;
        PageBean pageBean = new PageBean(posts.getTotal(),posts.getResult());
        redisTemplate.opsForValue().set(key,pageBean,10, TimeUnit.MINUTES);  // cache the result
        return pageBean;
    }

    @Override
    public PageBean getRecent(Integer page, Integer pageSize) {
        String key = "recent:" + page + ":" + pageSize;
        PageBean cachedPage = (PageBean) redisTemplate.opsForValue().get(key);
        if (cachedPage != null) {
            return cachedPage;
        }
        PageHelper.startPage(page, pageSize);
        List<PostNote> postNotes = postMapper.getPostsOrderedByCreateTime();
        Page<PostNote> posts = (Page<PostNote>) postNotes;
        PageBean pageBean = new PageBean(posts.getTotal(),posts.getResult());
        redisTemplate.opsForValue().set(key,pageBean,10, TimeUnit.MINUTES);  // cache the result
        return pageBean;
    }

    @Override
    public PageBean search(Integer page, Integer pageSize, String title, String content, String tagName) {
        PageHelper.startPage(page, pageSize);
        List<PostNote> postNotes = postMapper.search(title, content, tagName);
        Page<PostNote> posts = (Page<PostNote>) postNotes;
        PageBean pageBean = new PageBean(posts.getTotal(),posts.getResult());
        return pageBean;
    }

    @Override
    public PageBean getTags(Integer page, Integer pageSize) {
        String key = "tags:" + page + ":" + pageSize;
        PageBean cachedPage = (PageBean) redisTemplate.opsForValue().get(key);
        if (cachedPage != null) {
            return cachedPage;
        }
        PageHelper.startPage(page, pageSize);
        List<Tag> postNotes = postMapper.getTags();
        Page<Tag> posts = (Page<Tag>) postNotes;
        PageBean pageBean = new PageBean(posts.getTotal(),posts.getResult());
        redisTemplate.opsForValue().set(key,pageBean,10, TimeUnit.MINUTES);  // cache the result
        return pageBean;
    }


}
