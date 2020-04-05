package spring.app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.app.dao.abstraction.NotificationTemplateDao;
import spring.app.model.NotificationTemplate;
import spring.app.service.abstraction.NotificationTemplateService;

import java.util.Arrays;
import java.util.List;

@Service
public class NotificationTemplateServiceImpl implements NotificationTemplateService {
    private final NotificationTemplateDao notificationTemplateDao;

    @Autowired
    public NotificationTemplateServiceImpl(NotificationTemplateDao notificationTemplateDao) {
        this.notificationTemplateDao = notificationTemplateDao;
    }

    @Transactional(readOnly = true)
    @Override
    public List<NotificationTemplate> getAll() {
        return notificationTemplateDao.getAll();
    }

    @Transactional(readOnly = true)
    @Override
    public NotificationTemplate getById(Long id) {
        return notificationTemplateDao.getById(id);
    }

    @Transactional
    @Override
    public NotificationTemplate create(NotificationTemplate notificationTemplate) {
        notificationTemplateDao.save(notificationTemplate);
        return notificationTemplate;
    }

    @Transactional
    @Override
    public NotificationTemplate update(NotificationTemplate notificationTemplate) {
        notificationTemplateDao.update(notificationTemplate);
        return notificationTemplate;
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        notificationTemplateDao.deleteById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public NotificationTemplate getByName(String name) {
        return notificationTemplateDao.getByName(name);
    }
}
