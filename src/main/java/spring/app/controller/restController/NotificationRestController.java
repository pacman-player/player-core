package spring.app.controller.restController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import spring.app.model.Notification;
import spring.app.model.User;
import spring.app.service.abstraction.NotificationService;

import java.util.List;

import static org.springframework.security.core.context.SecurityContextHolder.getContext;

@RestController
@RequestMapping("/api/admin/notification")
public class NotificationRestController {
    private final static Logger LOGGER = LoggerFactory.getLogger("NotificationRestController");
    private NotificationService notificationService;

    @Autowired
    public NotificationRestController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping
    public List<Notification> getNotificationByUserId() {
        User user = (User) getContext().getAuthentication().getPrincipal();
        List<Notification> list = notificationService.getByUserId(user.getId());
        LOGGER.info("GET request '/notification' for User = {}. Result has {} lines",user.getLogin(), list.size());
        return list;
    }

    @PostMapping(value = "/read")
    public void readNotificationById(@RequestBody String Id) {
        Id = Id.replaceAll("[^A-Za-zА-Яа-я0-9 ]", "");
        Long id = Long.parseLong(Id);
        Notification notification = notificationService.getNotificationById(id);
        notification.setFlag(false);
        notificationService.updateNotification(notification);
        LOGGER.info("POST request '/notification/read' with id = {} ", id);
    }
}
