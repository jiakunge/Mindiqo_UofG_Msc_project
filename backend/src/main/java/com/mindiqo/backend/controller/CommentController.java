package com.mindiqo.backend.controller;

import com.mindiqo.backend.entity.Comment;
import com.mindiqo.backend.entity.Result;
import com.mindiqo.backend.service.CommentService;
import com.mindiqo.backend.service.NotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/api/posts")
public class CommentController {
    @Autowired
    private CommentService commentService;
    @Autowired
    private NotificationService notificationService;
    /**
     * Retrieves all comments associated with a specific post.
     *
     * Each comment includes: id, postId, userId, content, createTime, and user.
     * The user, nested within the comment, only includes the id, username, and avatar fields.
     *
     * @param postId The ID of the post whose comments are to be fetched.
     * @return A Result object containing a list of comments for the given post.
     */
    @RequestMapping(value = "/{postId}/comments", method = RequestMethod.GET)
    public Result getComments(@PathVariable Integer postId){
        log.info("Get comments of post with id: {}", postId);
        return Result.success(commentService.getComments(postId));
    }
    /**
     * Allows a user to add a comment to a specific post.
     *
     * The user's ID is obtained from the token in the request header.
     * The comment, received in the request body, must include the content and postId fields.
     *
     * @param comment The comment object to be added. It must include content and postId.
     * @param userId User's ID obtained from the token in the request header.
     * @return A Result object indicating the success of the commenting operation.
     */
    @RequestMapping(value = "/{postId}/comment", method = RequestMethod.POST)
    public Result comment(@RequestBody Comment comment, @RequestAttribute("userId") Integer userId){
        comment.setUserId(userId);
        log.info("Comment post: {}", comment);
        commentService.comment(comment);
        notificationService.sendCommentNotification(comment);
        return Result.success();
    }
}
