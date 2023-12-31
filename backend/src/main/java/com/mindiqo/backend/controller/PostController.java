package com.mindiqo.backend.controller;

import com.mindiqo.backend.entity.PageBean;
import com.mindiqo.backend.entity.PostNote;
import com.mindiqo.backend.entity.Result;
import com.mindiqo.backend.service.PostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/api/posts")
public class PostController {
    @Autowired
    private PostService postService;
    /**
     * Retrieves details of a specific post, including its content.
     *
     * @param id The ID of the post to be retrieved.
     * @return A Result object containing details of the post.
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Result getById(@PathVariable Integer id){
        log.info("Get post with id: {}", id);
        return Result.success(postService.getById(id));
    }
    /**
     * Deletes a post. The request must include a token in the header for
     * authentication, from which the userId will be extracted.
     *
     * @param postId The ID of the post to be deleted.
     * @param userId User's ID obtained from the token in the request header.
     * @return A Result object indicating the success of the deletion operation.
     */
    @RequestMapping(value = "/{postId}/delete", method = RequestMethod.DELETE)
    public Result deleteById(@PathVariable Integer postId, @RequestAttribute("userId") Integer userId){
        PostNote deletePost = new PostNote();
        deletePost.setId(postId);
        deletePost.setUserId(userId);
        log.info("Delete post with id: {}", postId);
        postService.delete(deletePost);
        return Result.success();
    }
    /**
     * Retrieves the most viewed posts. Pagination parameters can be optionally specified.
     *
     * @param page The page number. Default is 1.
     * @param pageSize The number of posts per page. Default is 6.
     * @return A Result object containing the most viewed posts.
     */
    @RequestMapping(value ="/most_viewed", method = RequestMethod.GET)
    public Result getMostViewed(@RequestParam(defaultValue = "1") Integer page,
                             @RequestParam(defaultValue = "6") Integer pageSize){
        log.info("Get popular posts");
        PageBean pageBean = postService.getMostViewed(page, pageSize);
        return Result.success(pageBean);
    }
    /**
     * Retrieves the most liked posts. Pagination parameters can be optionally specified.
     *
     * @param page The page number. Default is 1.
     * @param pageSize The number of posts per page. Default is 6.
     * @return A Result object containing the most viewed posts.
     */
    @RequestMapping(value ="/most_liked", method = RequestMethod.GET)
    public Result getMostLiked(@RequestParam(defaultValue = "1") Integer page,
                               @RequestParam(defaultValue = "6") Integer pageSize){
        log.info("Get popular posts");
        PageBean pageBean = postService.getMostLiked(page, pageSize);
        return Result.success(pageBean);
    }
    /**
     * Retrieves the recent posts. Pagination parameters can be optionally specified.
     *
     * @param page The page number. Default is 1.
     * @param pageSize The number of posts per page. Default is 6.
     * @return A Result object containing the most viewed posts.
     */
    @RequestMapping(value ="/recent", method = RequestMethod.GET)
    public Result getRecent(@RequestParam(defaultValue = "1") Integer page,
                               @RequestParam(defaultValue = "6") Integer pageSize){
        log.info("Get recent posts");
        PageBean pageBean = postService.getRecent(page, pageSize);
        return Result.success(pageBean);
    }
    /**
     * Retrieves posts based on a search query. The search can be performed
     * based on title, content, or tag. The results are paginated using a PageBean.
     * Note: Only one of the parameters title, content, or tag should be provided.
     *
     * @param page The page number (default is 1).
     * @param pageSize The number of posts per page (default is 6).
     * @param title Optional title keyword for the search.
     * @param content Optional content keyword for the search.
     * @param tag Optional tag keyword for the search.
     * @return A Result object containing paginated search results.
     */
    @RequestMapping(value ="/search", method = RequestMethod.GET)
    public Result search(@RequestParam(defaultValue = "1") Integer page,
                         @RequestParam(defaultValue = "6") Integer pageSize,
                         String title,
                         String content,
                        String tag){
        log.info("Search posts with keyword: {} {}", title, content, tag);
        PageBean pageBean = postService.search(page, pageSize, title, content, tag);
        return Result.success(pageBean);
    }
    /**
     * Retrieves a list of popular tags. Results are paginated using a PageBean.
     *
     * @param page The page number (default is 1).
     * @param pageSize The number of tags per page (default is 6).
     * @return A Result object containing paginated list of popular tags.
     */
    @RequestMapping(value = "/tags", method = RequestMethod.GET)
    public Result getTags(@RequestParam(defaultValue = "1") Integer page,
                         @RequestParam(defaultValue = "6") Integer pageSize){
        log.info("Get tags");
        PageBean pageBean = postService.getTags(page, pageSize);
        return Result.success(pageBean);
    }
}
