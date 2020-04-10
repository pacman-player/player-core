package core.app.dao.abstraction;

import core.app.model.Notification;

import java.util.List;

public interface NotificationDao extends GenericDao<Long, Notification> {
    void bulkRemoveNotificationsByUserId(Long userId);

    List<Notification> getByUserId(Long id);

}
