package spring.app.dao.impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import spring.app.dao.abstraction.NotificationDao;
import spring.app.model.Notification;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class NotificationDaoImpl extends AbstractDao<Long, Notification> implements NotificationDao {
    public NotificationDaoImpl() {
        super(Notification.class);
    }

    @Override
    public List<Notification> getByUserId(Long id) {
        TypedQuery<Notification> query = entityManager.createQuery("FROM Notification WHERE user_id = :id", Notification.class);
        query.setParameter("id", id);

        List<Notification> list = query.getResultList();
        return list;
    }

    @Override
    public void bulkRemoveNotificationsByUserId(Long userId) {
        entityManager.createQuery("DELETE FROM Notification WHERE user_id = :userId")
                .setParameter("userId", userId)
                .executeUpdate();
        entityManager.flush();
    }
}
