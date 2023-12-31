package com.mindiqo.backend.controller;

import com.mindiqo.backend.entity.Result;
import com.mindiqo.backend.entity.User;
import com.mindiqo.backend.service.AuthService;
import com.mindiqo.backend.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


@Slf4j
@RestController
@RequestMapping(value = "/api")
public class AuthController {
    @Autowired
    private AuthService authService;
    /**
     * Authenticates a user based on their username and password.
     * Upon successful authentication, a JWT token is returned.
     *
     * @param user Object containing the username and password of the user.
     * @return Result Object indicating the success or failure of the authentication process.
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Result login(@RequestBody User user){
        log.info("login user: {}", user);
        User authenticatedUser = authService.login(user);
        if (authenticatedUser != null){
            Map<String, Object> claims = Map.of(
                    "id", authenticatedUser.getId(),
                    "username", authenticatedUser.getUsername(),
                    "avatar", authenticatedUser.getAvatar()
            );

            String token = JwtUtils.generateJwt(claims);

            return Result.success(token);
        }

        return Result.error("invalid username and password");
    }
    /**
     * Registers a new user based on their username, password, and email.
     *
     * @param user Object containing the username, password, and email of the user.
     * @return Result Object indicating the success or failure of the registration process.
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public Result register(@RequestBody User user){
        log.info("register user: {}", user);
        boolean isSuccess =  authService.register(user);
        if (!isSuccess){
            return Result.error("username already exists");
        }else {
            return Result.success();
        }
    }
    /**
     * Registers a new user based on their username, password, and email.
     *
     * @param user Object containing the username, password, and email of the user.
     * @return Result Object indicating the success or failure of the registration process.
     */
    @RequestMapping(value = "/reset_password", method = RequestMethod.POST)
    public Result resetPassword(@RequestBody User user){
        log.info("reset password for user: {}", user);
        authService.resetPassword(user);
        return Result.success();
    }
}
