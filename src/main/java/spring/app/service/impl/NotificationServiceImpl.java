package spring.app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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

    @Autowired
    public NotificationServiceImpl(NotificationDao notificationDao, UserDao userDao) {
        this.notificationDao = notificationDao;
        this.userDao = userDao;
    }

    @Override
    public void addNotification(Notification notification) {
        notificationDao.save(notification);
    }

    @Override
    public void addNotification(String message, Long user_id) {
        List<User> users = userDao.getAll();
        for (User user: users){
            if(user.getId() != user_id){
                Notification notification = new Notification(message,user);
                notificationDao.save(notification);
            }
        }
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
