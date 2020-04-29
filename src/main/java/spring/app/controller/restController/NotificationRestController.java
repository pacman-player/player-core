package spring.app.controller.restController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import spring.app.dto.NotificationDto;
import spring.app.model.Notification;
import spring.app.model.User;
import spring.app.service.abstraction.NotificationService;

import java.util.List;

import static org.springframework.security.core.context.SecurityContextHolder.getContext;

@RestController
@RequestMapping("/api/admin/notification")
public class NotificationRestController {
    private final static Logger LOGGER = LoggerFactory.getLogger(NotificationRestController.class);
    private NotificationService notificationService;

    @Autowired
    public NotificationRestController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping
    public List<Notification> getNotificationByUserId() {
        User user = (User) getContext().getAuthentication().getPrincipal();
        List<Notification> list = notificationService.getByUserId(user.getId());
        return list;
    }

    @GetMapping("/all")
    public List<NotificationDto> getAllNotifications() {
        return notificationService.getAllNotificationDto();
    }

    @GetMapping("/{id}")
    public Notification getNotificationById(@PathVariable String id) {
        return notificationService.getById(Long.parseLong(id));
    }

    @PostMapping(value = "/read")
    public void readNotificationById(@RequestBody String stringId) {
        LOGGER.info("POST request '/notification/read' with id = {}", stringId);
        stringId = stringId.replaceAll("[^A-Za-zА-Яа-я0-9 ]", "");
        Long id = Long.parseLong(stringId);
        Notification notification = notificationService.getById(id);
        notification.setFlag(false);
        notificationService.update(notification);
    }

    @PostMapping
    public void createNotification(@RequestBody Notification notification) {
        LOGGER.info("POST request '/notification' with id = {}", notification.getId());
        notification.setFlag(true);
        notification.setId(null);
        notificationService.save(notification);
        LOGGER.info("Created Notification {}", notification);
    }

    @PostMapping("/admin")
    public void createAdminNotification(@RequestBody Notification notification) {
        LOGGER.info("POST request '/notification/global' with notification = {}", notification);
        try {
            notificationService.save(notification.getMessage());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @PutMapping
    public void updateNotification(@RequestBody Notification notification) {
        LOGGER.info("PUT request '/notification' with notification = {}", notification);
        notification.setUser(notificationService.getById(notification.getId()).getUser());
        notification.setFlag(true);
        notificationService.update(notification);
    }

    @DeleteMapping
    public void deleteNotificationById(@RequestBody Notification notification) {
        LOGGER.info("DELETE request '/notification' with id = {}", notification.getId());
        notificationService.deleteById(notification.getId());
    }

    @DeleteMapping("/user/{id}")
    public void deleteAllUserNotifications(@PathVariable String id) {
        LOGGER.info("DELETE request '/notification/user/{}'", id);
        notificationService.deleteAllNotificationsFromUser(Long.parseLong(id));
    }

}
