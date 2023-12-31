package com.mindiqo.backend.service;

import com.mindiqo.backend.entity.PostNote;
import com.mindiqo.backend.entity.User;

import java.util.List;

public interface AuthService {


    User login(User user);

    boolean register(User user);

    void resetPassword(User user);

    User getUser(User user);

    List<PostNote> getUserPosts(User user);

    void updateUser(User user);
}
