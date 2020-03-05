package spring.app.dao.abstraction;

import spring.app.model.Notification;

import java.util.List;

public interface NotificationDao extends GenericDao<Long, Notification> {
    void bulkRemoveNotificationsByUserId(Long userId);

    List<Notification> getByUserId(Long id);

}
