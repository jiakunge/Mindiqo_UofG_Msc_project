package com.mindiqo.backend.controller;

import com.mindiqo.backend.entity.Like;
import com.mindiqo.backend.entity.Result;
import com.mindiqo.backend.service.LikeService;
import com.mindiqo.backend.service.NotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/api/posts")
public class LikeController {
    @Autowired
    private LikeService likeService;
    @Autowired
    private NotificationService notificationService;
    /**
     * Allows a user to like a post.
     *
     * @param postId The ID of the post to be liked.
     * @param userId User's ID obtained from the token in the request header.
     * @return A Result object indicating the success of the like operation.
     */
    @RequestMapping(value = "/{postId}/like", method = RequestMethod.POST)
    public Result like(@PathVariable Integer postId, @RequestAttribute("userId") Integer userId){
        log.info("User {} Like post with id: {}", userId,postId);
        Like newLike = new Like();
        newLike.setPostId(postId);
        newLike.setUserId(userId);
        likeService.like(newLike);
        notificationService.sendLikeNotification(newLike);
        return Result.success();
    }
    /**
     * Allows a user to unlike a post.
     *
     * @param postId The ID of the post to be unliked.
     * @param userId User's ID obtained from the token in the request header.
     * @return A Result object indicating the success of the unlike operation.
     */
    @RequestMapping(value = "/{postId}/unlike", method = RequestMethod.DELETE)
    public Result unlike(@PathVariable Integer postId, @RequestAttribute("userId") Integer userId){
        log.info("User {} Unlike post with id: {}", userId,postId);
        Like newLike = new Like();
        newLike.setPostId(postId);
        newLike.setUserId(userId);
        likeService.unlike(newLike);
        return Result.success();
    }
    /**
     * Retrieves the total number of likes for a post.
     *
     * @param id The ID of the post.
     * @return A Result object containing the number of likes.
     */
    @RequestMapping(value = "/{id}/likes", method = RequestMethod.GET)
    public Result getLikes(@PathVariable Integer id){
        log.info("Get likes of post with id: {}", id);
        return Result.success(likeService.getLikes(id));
    }
    /**
     * Checks whether a user has liked a specific post.
     *
     * @param postId The ID of the post to be checked.
     * @param userId User's ID obtained from the token in the request header.
     * @return A Result object with a boolean value indicating if the post was liked by the user.
     */
    //  返回值 boolean携带在 Result 中
    @RequestMapping(value = "/{postId}/is_liked", method = RequestMethod.GET)
    public Result isLiked(@PathVariable Integer postId, @RequestAttribute("userId") Integer userId){
        log.info("User {} isLiked post with id: {}", userId,postId);
        Like newLike = new Like();
        newLike.setPostId(postId);
        newLike.setUserId(userId);
        return Result.success(likeService.isLiked(newLike));
    }

}
