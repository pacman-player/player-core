package core.app.service.abstraction;

import core.app.model.Author;
import core.app.model.Notification;

import java.util.List;

public interface NotificationService {

    void addNotification(Notification notification);

    void updateNotification(Notification notification);

    void addNotification(String message,Long id) throws InterruptedException;

    void addNotification(String message) throws InterruptedException;

    void addNotification(Author author) throws InterruptedException;

    List<Notification> getAllNotification();

    List<Notification> getByUserId(Long id);

    Notification getNotificationById(Long id);

    void deleteNotificationById(Long id);

    void removeAllNotificationsFromUser(Long userId);
}
