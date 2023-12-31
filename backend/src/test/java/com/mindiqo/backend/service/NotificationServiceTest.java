package com.mindiqo.backend.service;

import com.mindiqo.backend.entity.Comment;
import com.mindiqo.backend.entity.Like;
import com.mindiqo.backend.entity.Notification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
public class NotificationServiceTest {

    @Mock
    private NotificationService notificationService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetUnread() {
        // Given
        Integer userId = 1;
        Notification notification = new Notification();
        notification.setId(1);
        notification.setReceiverId(userId);

        when(notificationService.getUnread(userId)).thenReturn(Arrays.asList(notification));

        // When
        List<Notification> notifications = notificationService.getUnread(userId);

        // Then
        assertEquals(1, notifications.size());
        assertEquals(notification, notifications.get(0));
    }

    @Test
    public void testGetRead() {
        // Given
        Integer userId = 1;
        Notification notification = new Notification();
        notification.setId(1);
        notification.setReceiverId(userId);

        when(notificationService.getRead(userId)).thenReturn(Arrays.asList(notification));

        // When
        List<Notification> notifications = notificationService.getRead(userId);

        // Then
        assertEquals(1, notifications.size());
        assertEquals(notification, notifications.get(0));
    }

    @Test
    public void testReadANotification() {
        // Given
        Notification notification = new Notification();
        notification.setId(1);

        doNothing().when(notificationService).readANotification(notification);

        // When
        notificationService.readANotification(notification);

        // Then
        verify(notificationService, times(1)).readANotification(notification);
    }

    @Test
    public void testDeleteById() {
        // Given
        Notification notification = new Notification();
        notification.setId(1);

        doNothing().when(notificationService).deleteById(notification);

        // When
        notificationService.deleteById(notification);

        // Then
        verify(notificationService, times(1)).deleteById(notification);
    }

    @Test
    public void testGetUnreadCount() {
        // Given
        Integer userId = 1;
        when(notificationService.getUnreadCount(userId)).thenReturn(5);

        // When
        Integer count = notificationService.getUnreadCount(userId);

        // Then
        assertEquals(5, count);
    }

    @Test
    public void testSendLikeNotification() {
        // Given
        Like newLike = new Like();
        newLike.setId(1);

        doNothing().when(notificationService).sendLikeNotification(newLike);

        // When
        notificationService.sendLikeNotification(newLike);

        // Then
        verify(notificationService, times(1)).sendLikeNotification(newLike);
    }

    @Test
    public void testSendCommentNotification() {
        // Given
        Comment comment = new Comment();
        comment.setId(1);

        doNothing().when(notificationService).sendCommentNotification(comment);

        // When
        notificationService.sendCommentNotification(comment);

        // Then
        verify(notificationService, times(1)).sendCommentNotification(comment);
    }
}
