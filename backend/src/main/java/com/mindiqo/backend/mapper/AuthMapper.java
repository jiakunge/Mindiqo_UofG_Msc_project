package com.mindiqo.backend.mapper;

import com.mindiqo.backend.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AuthMapper {
    public User login(User user);
    public void insertUser(User user);

    public void updatePassword(User user);

    public User getBasicInfoById(Integer userId);

    User getUser(User user);

    void updateUser(User user);
}
