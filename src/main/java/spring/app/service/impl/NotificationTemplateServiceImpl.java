package spring.app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.app.dao.abstraction.NotificationTemplateDao;
import spring.app.model.NotificationTemplate;
import spring.app.service.abstraction.NotificationTemplateService;

import java.util.List;

@Service
public class NotificationTemplateServiceImpl extends AbstractServiceImpl<Long, NotificationTemplate, NotificationTemplateDao> implements NotificationTemplateService {

    @Autowired
    public NotificationTemplateServiceImpl(NotificationTemplateDao dao) {
        super(dao);
    }

    @Transactional(readOnly = true)
    @Override
    public List<NotificationTemplate> getAll() {
        return dao.getAll();
    }

    @Transactional(readOnly = true)
    @Override
    public NotificationTemplate getById(Long id) {
        return dao.getById(id);
    }

    @Transactional
    @Override
    public NotificationTemplate create(NotificationTemplate notificationTemplate) {
        dao.save(notificationTemplate);
        return notificationTemplate;
    }


    // так как метод возвращает сущность переименовал метод в updateAndGet . метод update конфликт с абстрактнымсервисом
    @Transactional
    @Override
    public NotificationTemplate updateAndGet(NotificationTemplate notificationTemplate) {
        dao.update(notificationTemplate);
        return notificationTemplate;
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        dao.deleteById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public NotificationTemplate getByName(String name) {
        return dao.getByName(name);
    }
}
