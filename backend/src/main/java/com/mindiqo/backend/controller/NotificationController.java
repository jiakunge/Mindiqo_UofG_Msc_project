package com.mindiqo.backend.controller;

import com.mindiqo.backend.entity.Notification;
import com.mindiqo.backend.entity.Result;
import com.mindiqo.backend.service.NotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/api/notifications")
public class NotificationController {
    @Autowired
    private NotificationService notificationService;

    /**
     * Retrieves all unread notifications for a user.
     *
     * @param userId User's ID obtained from the token in the request header.
     * @return A Result object containing a list of unread notifications.
     */
    @RequestMapping(value = "/unread", method = RequestMethod.GET)
    public Result getUnreadNotifications(@RequestAttribute("userId") Integer userId){
        log.info("Get unread notifications of user {}", userId);
        return Result.success(notificationService.getUnread(userId));
    }

    /**
     * Retrieves all read notifications for a user.
     *
     * @param userId User's ID obtained from the token in the request header.
     * @return A Result object containing a list of read notifications.
     */
    @RequestMapping(value = "/read", method = RequestMethod.GET)
    public Result getReadNotifications(@RequestAttribute("userId") Integer userId){
        log.info("Get read notifications of user {}", userId);
        return Result.success(notificationService.getRead(userId));
    }
    /**
     * Marks a specific unread notification as read.
     *
     * @param userId User's ID obtained from the token in the request header.
     * @param notification Notification object containing the ID of the notification to be marked as read.
     * @return A Result object indicating the success of the operation.
     */
    @RequestMapping(value = "/read", method = RequestMethod.POST)
    public Result readANotification(@RequestAttribute("userId") Integer userId, @RequestBody Notification notification){
        notification.setReceiverId(userId);
        log.info("Read a notifications of user {}", userId);
        notificationService.readANotification(notification);
        return Result.success();
    }
    /**
     * Deletes a specific notification for a user.
     *
     * @param id The ID of the notification to be deleted.
     * @param userId User's ID obtained from the token in the request header.
     * @return A Result object indicating the success of the deletion operation.
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Result delete(@PathVariable Integer id, @RequestAttribute("userId") Integer userId){
        Notification notification = new Notification();
        notification.setId(id);
        notification.setReceiverId(userId);
        log.info("Delete notification with id: {}", id);
        notificationService.deleteById(notification);
        return Result.success();
    }
    /**
     * Retrieves the count of unread notifications for a user.
     *
     * @param userId User's ID obtained from the token in the request header.
     * @return A Result object containing the count of unread notifications.
     */
    @RequestMapping(value = "/unread_count", method = RequestMethod.GET)
    public Result getUnreadCount(@RequestAttribute("userId") Integer userId){
        log.info("Get unread count of user {}", userId);
        return Result.success(notificationService.getUnreadCount(userId));
    }
}
