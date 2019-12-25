package spring.app.service.abstraction;

import spring.app.model.Notification;

import java.util.List;

public interface NotificationService {
    void addNotification(Notification notification);

    List<Notification> getAllNotification();

    List<Notification> getByUserId(Long id);

    void deleteNotificationById(Long id);
}
