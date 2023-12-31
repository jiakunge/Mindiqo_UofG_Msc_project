package com.mindiqo.backend.controller;

import com.mindiqo.backend.entity.PostNote;
import com.mindiqo.backend.entity.Result;
import com.mindiqo.backend.entity.User;
import com.mindiqo.backend.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/api/user")
public class UserController {
    @Autowired
    private AuthService userService;
    /**
     * Retrieves a user based on the provided username.
     *
     * @param username The username of the desired user.
     * @return A Result object containing user details like ID, username, avatar, creation time, update time, email, bio, and background image.
     */
    @RequestMapping(value = "/{username}", method = RequestMethod.GET)
    public Result getUser(@PathVariable String username){
        log.info("Get user with username: {}", username);
        User user = new User();
        user.setUsername(username);
        return Result.success(userService.getUser(user));
    }
    /**
     * Fetches the posts associated with a particular user.
     *
     * @param user The user whose posts need to be fetched.
     * @return A Result object containing a list of PostNote associated with the user.
     */
    @RequestMapping(value = "/post", method = RequestMethod.POST)
    public  Result getUserPosts(@RequestBody User user){
        log.info("get user {} posts", user);
        List<PostNote> posts =  userService.getUserPosts(user);
        return Result.success(posts);
    }
    /**
     * Updates user information.
     *
     * @param user The user data that needs to be updated. Fields like avatar, bio, and background image are optional.
     * @param userId The ID of the user being updated. This ensures that users can only update their own profiles.
     * @return A Result object indicating success or failure of the update operation.
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Result updateUser(@RequestBody User user, @RequestAttribute("userId") Integer userId){
        user.setId(userId);
        log.info("update user {}", user);
        userService.updateUser(user);
        return Result.success();
    }
}
