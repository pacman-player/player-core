package spring.app.controller.restController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import spring.app.model.NotificationTemplate;
import spring.app.service.abstraction.NotificationTemplateService;

import java.util.List;

@Controller
@RequestMapping("/api/admin/notification/template")
public class NotificationTemplateRestController {
    private final NotificationTemplateService notificationTemplateService;

    @Autowired
    public NotificationTemplateRestController(NotificationTemplateService notificationTemplateService) {
        this.notificationTemplateService = notificationTemplateService;
    }

    @GetMapping()
    public List<NotificationTemplate> getAll() {
        return notificationTemplateService.getAll();
    }

    @GetMapping("{id}")
    public NotificationTemplate getById(@PathVariable String id) {
        return notificationTemplateService.getById(Long.parseLong(id));
    }

    @PostMapping
    public NotificationTemplate create(
            @RequestBody NotificationTemplate notificationTemplate) {
        return notificationTemplateService.create(notificationTemplate);
    }

    @PutMapping
    public NotificationTemplate update(@RequestBody NotificationTemplate notificationTemplate) {
        return notificationTemplateService.update(notificationTemplate);
    }

    @DeleteMapping("{id}")
    public void deleteById(@PathVariable String id) {
        notificationTemplateService.deleteById(Long.parseLong(id));
    }
}
