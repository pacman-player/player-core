package core.app.dao.abstraction;

import core.app.model.NotificationTemplate;

public interface NotificationTemplateDao extends GenericDao<Long, NotificationTemplate> {
    NotificationTemplate getByName(String name);
}
