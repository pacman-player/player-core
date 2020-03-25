package spring.app.dao.impl;

import org.springframework.stereotype.Repository;
import spring.app.dao.abstraction.NotificationTemplateDao;
import spring.app.model.NotificationTemplate;

import javax.persistence.Query;

@Repository
public class NotificationTemplateDaoImpl extends AbstractDao<Long, NotificationTemplate> implements NotificationTemplateDao {
    public NotificationTemplateDaoImpl() {
        super(NotificationTemplate.class);
    }

    @Override
    public NotificationTemplate getByName(String name) {
        Query query = entityManager.createQuery("FROM NotificationTemplate WHERE name = :name");
        query.setParameter("name", name);
        return (NotificationTemplate) query.getSingleResult();
    }
}
