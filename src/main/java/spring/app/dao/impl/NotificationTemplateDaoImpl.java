package spring.app.dao.impl;

import org.springframework.stereotype.Repository;
import spring.app.dao.abstraction.NotificationTemplateDao;
import spring.app.model.NotificationTemplate;

@Repository
public class NotificationTemplateDaoImpl extends AbstractDao<Long, NotificationTemplate> implements NotificationTemplateDao {
    NotificationTemplateDaoImpl() {
        super(NotificationTemplate.class);
    }
}
