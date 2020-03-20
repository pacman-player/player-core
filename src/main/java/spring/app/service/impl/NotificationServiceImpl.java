package spring.app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.app.dao.abstraction.NotificationDao;
import spring.app.dao.abstraction.UserDao;
import spring.app.model.Author;
import spring.app.model.Notification;
import spring.app.model.NotificationTemplate;
import spring.app.model.User;
import spring.app.service.abstraction.NotificationService;

import java.util.List;

@Service
@Transactional
@EnableAsync(proxyTargetClass = true)
@EnableCaching(proxyTargetClass = true)
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
    }

    @Override
    public void addNotification(String message) throws InterruptedException {
        List<User> usersAdmin = userDao.getUserByRole("ADMIN");
        for (User user : usersAdmin) {
            Notification notification = new Notification(message, true, user);
            notificationDao.save(notification);
        }
    }

    @Override
    public void addNotification(Author author, NotificationTemplate notificationTemplate) throws InterruptedException {
        String template = notificationTemplate.getTemplate();
        try {
            String linkMeta, link;
            linkMeta = template.substring(template.indexOf("{link:"),
                    template.indexOf(":}") + 2);
            String[] linkDetails = linkMeta.substring(6, linkMeta.length() - 2).split(":");
            link = "<a href=\"" + linkDetails[0] + "\">" + linkDetails[1] + "</a>";
            template = template.replace(linkMeta, link);
        } catch (StringIndexOutOfBoundsException ignore) {
            //ignored
        }
        String message = template;
        message = message.replaceAll("\\{subject}", author.getName());
        List<User> usersAdmin = userDao.getUserByRole("ADMIN");
        for (User user : usersAdmin) {
            Notification notification = new Notification(message, true, user);
            notificationDao.save(notification);
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

    @Override
    public void removeAllNotificationsFromUser(Long userId) {
        notificationDao.bulkRemoveNotificationsByUserId(userId);
    }
}
