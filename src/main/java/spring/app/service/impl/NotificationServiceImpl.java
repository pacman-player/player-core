package spring.app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.app.dao.abstraction.MessageDao;
import spring.app.dao.abstraction.NotificationDao;
import spring.app.dao.abstraction.UserDao;
import spring.app.model.Author;
import spring.app.model.Message;
import spring.app.model.Notification;
import spring.app.model.User;
import spring.app.service.abstraction.NotificationService;

import java.util.List;

@Service
@Transactional
@EnableAsync(proxyTargetClass = true)
@EnableCaching(proxyTargetClass = true)
public class NotificationServiceImpl extends AbstractService<Notification, NotificationDao> implements NotificationService {

    private UserDao userDao;

    @Autowired
    public NotificationServiceImpl(NotificationDao dao, UserDao userDao) {
        super(dao);
        this.userDao = userDao;
    }

    @Override
    public void addNotification(Notification notification) {
        dao.save(notification);
    }

    @Override
    public void updateNotification(Notification notification) {
        dao.update(notification);
    }

    @Override
    public Notification getNotificationById(Long id) {
        return dao.getById(id);
    }

    @Override
    public void addNotification(String message, Long id) throws InterruptedException {
        List<User> users = userDao.getAll();
        for (User user : users) {
            if (!user.getId().equals(id)) {
                Notification notification = new Notification(message, true, user);
                dao.save(notification);
            }
        }
    }

    @Override
    public void addNotification(String message) throws InterruptedException {
        List<User> usersAdmin = userDao.getUserByRole("ADMIN");
        for (User user : usersAdmin) {
            Notification notification = new Notification(message, true, user);
            dao.save(notification);
        }
    }

    @Override
    public void addNotification(Author author) throws InterruptedException {
        List<User> usersAdmin = userDao.getUserByRole("ADMIN");
        for (User user : usersAdmin) {
            Notification notification = new Notification("{patterned}" + author.getName(), true, user);
            dao.save(notification);
        }
    }

    @Override
    public List<Notification> getAllNotification() {
        return dao.getAll();
    }

    @Override
    public List<Notification> getByUserId(Long id) {
        return dao.getByUserId(id);
    }

    @Override
    public void deleteNotificationById(Long id) {
        dao.deleteById(id);
    }

    @Override
    public void removeAllNotificationsFromUser(Long userId) {
        dao.bulkRemoveNotificationsByUserId(userId);
    }
}
