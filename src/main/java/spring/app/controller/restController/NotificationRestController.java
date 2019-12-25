package spring.app.controller.restController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.app.model.Notification;
import spring.app.model.User;
import spring.app.service.abstraction.NotificationService;

import java.util.List;

import static org.springframework.security.core.context.SecurityContextHolder.getContext;

@RestController
@RequestMapping("/api/admin/notification")
public class NotificationRestController {

    private NotificationService notificationService;

    @Autowired
    public NotificationRestController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping
    public List<Notification> getNotificationByUserId() {
        User user = (User) getContext().getAuthentication().getPrincipal();
        return notificationService.getByUserId(user.getId());
    }
}
