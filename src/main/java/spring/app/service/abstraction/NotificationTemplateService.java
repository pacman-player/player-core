package spring.app.service.abstraction;

import spring.app.model.NotificationTemplate;

public interface NotificationTemplateService extends GenericService<Long, NotificationTemplate> {

    NotificationTemplate create(NotificationTemplate notificationTemplate);

    NotificationTemplate updateAndGet(NotificationTemplate notificationTemplate);

    NotificationTemplate getByName(String name);
}
