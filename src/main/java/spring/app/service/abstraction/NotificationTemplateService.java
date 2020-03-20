package spring.app.service.abstraction;

import spring.app.model.NotificationTemplate;

import java.util.List;

public interface NotificationTemplateService {
    List<NotificationTemplate> getAll();

    NotificationTemplate getById(Long id);

    NotificationTemplate create(NotificationTemplate notificationTemplate);

    NotificationTemplate update(NotificationTemplate notificationTemplate);

    void deleteById(Long id);

    NotificationTemplate getByName(String name);
}
