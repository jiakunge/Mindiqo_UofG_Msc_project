package com.mindiqo.backend.service.impl;

import com.mindiqo.backend.entity.PostNote;
import com.mindiqo.backend.entity.User;
import com.mindiqo.backend.mapper.AuthMapper;
import com.mindiqo.backend.mapper.PostMapper;
import com.mindiqo.backend.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private AuthMapper authMapper;
    @Autowired
    private PostMapper postMapper;

    @Override
    public User login(User user) {
        return authMapper.login(user);
    }

    @Override
    public boolean register(User user) {
        user.setUpdateTime(LocalDateTime.now());
        user.setCreateTime(LocalDateTime.now());
        String username = user.getUsername();
        // use ui-avatars.com to generate avatar
        // ui-avatars.com will generate a random avatar based on the first letter of username
        String avatarUrl = "https://robohash.org/"+username+"?set=set4&bgset=&size=150x150";
        user.setAvatar(avatarUrl);
         try{
             authMapper.insertUser(user);
             return true;
         }catch (DataIntegrityViolationException e) {
             // catch duplicate username exception and return false
             e.printStackTrace();
             return false;
         }

    }

    @Override
    public void resetPassword(User user) {
        user.setUpdateTime(LocalDateTime.now());
        authMapper.updatePassword(user);
    }

    @Override
    public User getUser(User user) {
        return authMapper.getUser(user);
    }

    @Override
    public List<PostNote> getUserPosts(User user) {
        return postMapper.getUserPosts(user);
    }

    @Override
    public void updateUser(User user) {
        user.setUpdateTime(LocalDateTime.now());
        authMapper.updateUser(user);
    }
}
