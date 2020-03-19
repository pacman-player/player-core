package spring.app.controller.restController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import spring.app.model.NotificationTemplate;
import spring.app.service.abstraction.NotificationTemplateService;

import java.util.List;

@RestController
@RequestMapping("/api/admin/notification/template")
public class NotificationTemplateRestController {
    private final static Logger LOGGER = LoggerFactory.getLogger(NotificationTemplateRestController.class);
    private final NotificationTemplateService notificationTemplateService;

    @Autowired
    public NotificationTemplateRestController(NotificationTemplateService notificationTemplateService) {
        this.notificationTemplateService = notificationTemplateService;
    }

    @GetMapping()
    public List<NotificationTemplate> getAll() {
        LOGGER.info("GET request '/template'");
        return notificationTemplateService.getAll();
    }

    @GetMapping("{id}")
    public NotificationTemplate getById(@PathVariable String id) {
        LOGGER.info("GET request '/template/{}'", id);
        return notificationTemplateService.getById(Long.parseLong(id));
    }

    @PostMapping
    public NotificationTemplate create(
            @RequestBody NotificationTemplate notificationTemplate) {
        LOGGER.info("POST request '/template' with template {}", notificationTemplate);
        notificationTemplate.setId(null);
        return notificationTemplateService.create(notificationTemplate);
    }

    @PutMapping
    public NotificationTemplate update(@RequestBody NotificationTemplate notificationTemplate) {
        LOGGER.info("PUT request '/template' with template {}", notificationTemplate);
        return notificationTemplateService.update(notificationTemplate);
    }

    @DeleteMapping("{id}")
    public void deleteById(@PathVariable String id) {
        LOGGER.info("DELETE request '/template/{}'", id);
        notificationTemplateService.deleteById(Long.parseLong(id));
    }
}
