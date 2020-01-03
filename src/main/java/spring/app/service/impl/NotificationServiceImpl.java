package spring.app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.app.controller.controller.NotificationController;
import spring.app.dao.abstraction.NotificationDao;
import spring.app.dao.abstraction.UserDao;
import spring.app.model.Notification;
import spring.app.model.User;
import spring.app.service.abstraction.NotificationService;

import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {

    private NotificationDao notificationDao;
    private UserDao userDao;
    private NotificationController notificationController;


    @Autowired
    public NotificationServiceImpl(NotificationDao notificationDao, UserDao userDao, NotificationController notificationController) {
        this.notificationDao = notificationDao;
        this.userDao = userDao;
        this.notificationController = notificationController;
    }


    @Override
    public void addNotification(Notification notification) {
        notificationDao.save(notification);
    }

    @Override
    public void updateNotification(Notification notification) {
        notificationDao.update(notification);
    }

    @Override
    public Notification getNotificationById(Long id) {
        return notificationDao.getById(id);
    }

    @Override
    public void addNotification(String message, Long id) throws InterruptedException {
        List<User> users = userDao.getAll();
        for (User user : users) {
            if (!user.getId().equals(id)) {
                Notification notification = new Notification(message, true, user);
                notificationDao.save(notification);
            }
        }
        notificationController.getNotification();
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
