package spring.app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.app.dao.abstraction.NotificationDao;
import spring.app.dao.abstraction.UserDao;
import spring.app.dao.abstraction.dto.NotificationDtoDao;
import spring.app.dto.NotificationDto;
import spring.app.model.Author;
import spring.app.model.Notification;
import spring.app.model.User;
import spring.app.service.abstraction.NotificationService;

import java.util.List;

@Service
@Transactional
@EnableAsync(proxyTargetClass = true)
@EnableCaching(proxyTargetClass = true)
public class NotificationServiceImpl extends AbstractServiceImpl<Long, Notification, NotificationDao> implements NotificationService {

    private UserDao userDao;
    private final NotificationDtoDao notificationDtoDao;

    @Autowired
    public NotificationServiceImpl(NotificationDao dao, NotificationDtoDao notificationDtoDao, UserDao userDao) {
        super(dao);
        this.notificationDtoDao = notificationDtoDao;
        this.userDao = userDao;
    }

    @Override
    public void save(String message, Long id) throws InterruptedException {
        List<User> users = userDao.getAll();
        for (User user : users) {
            if (!user.getId().equals(id)) {
                Notification notification = new Notification(message, true, user);
                dao.save(notification);
            }
        }
    }

    @Override
    public void save(String message) throws InterruptedException {
        List<User> usersAdmin = userDao.getUserByRole("ADMIN");
        for (User user : usersAdmin) {
            Notification notification = new Notification(message, true, user);
            dao.save(notification);
        }
    }

    @Override
    public void save(Author author) throws InterruptedException {
        List<User> usersAdmin = userDao.getUserByRole("ADMIN");
        for (User user : usersAdmin) {
            Notification notification = new Notification("{patterned}" + author.getName(), true, user);
            dao.save(notification);
        }
    }

    @Override
    public List<NotificationDto> getAllNotificationDto() {
        return notificationDtoDao.getAll();
    }

    @Override
    public List<Notification> getByUserId(Long id) {
        return dao.getByUserId(id);
    }

    @Override
    public void deleteAllNotificationsFromUser(Long userId) {
        dao.bulkRemoveNotificationsByUserId(userId);
    }
}
