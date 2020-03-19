package spring.app.dao.impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import spring.app.dao.abstraction.NotificationTemplateDao;
import spring.app.model.NotificationTemplate;

import javax.annotation.PostConstruct;

@Repository
public class NotificationTemplateDaoImpl extends AbstractDao<Long, NotificationTemplate> implements NotificationTemplateDao {
    NotificationTemplateDaoImpl() {
        super(NotificationTemplate.class);
    }
}
