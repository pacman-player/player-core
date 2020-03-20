package spring.app.dao.abstraction;

import spring.app.model.NotificationTemplate;

public interface NotificationTemplateDao extends GenericDao<Long, NotificationTemplate> {
    NotificationTemplate getByName(String name);
}
