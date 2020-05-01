package spring.app.service.abstraction;

import spring.app.dto.NotificationDto;
import spring.app.model.Author;
import spring.app.model.Notification;

import java.util.List;

public interface NotificationService extends GenericService<Long, Notification>{

    void save(String message, Long id) throws InterruptedException;

    void save(String message) throws InterruptedException;

    void save(Author author) throws InterruptedException;

    List<NotificationDto> getAllNotificationDto();

    List<Notification> getByUserId(Long id);

    void deleteAllNotificationsFromUser(Long userId);
}
