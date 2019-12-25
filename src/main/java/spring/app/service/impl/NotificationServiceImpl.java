package spring.app.service.impl;

import org.springframework.stereotype.Service;
import spring.app.dao.abstraction.NotificationDao;
import spring.app.model.Notification;
import spring.app.service.abstraction.NotificationService;

import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {

    private NotificationDao notificationDao;

    public NotificationServiceImpl(NotificationDao notificationDao) {
        this.notificationDao = notificationDao;
    }

    @Override
    public void addNotification(Notification notification) {
        notificationDao.save(notification);
    }

    @Override
    public List<Notification> getAllNotification() {
        return notificationDao.getAll();
    }

    @Override
    public List<Notification> getByUserId(Long id) {
        return notificationDao.getByUserId(id);
    }

    @Override
    public void deleteNotificationById(Long id) {
        notificationDao.deleteById(id);
    }
}
