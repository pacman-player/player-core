package spring.app.dao.impl;

import org.hibernate.Hibernate;
import org.springframework.stereotype.Repository;
import spring.app.dao.abstraction.NotificationDao;
import spring.app.model.Notification;

import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class NotificationDaoImpl extends AbstractDao<Long, Notification> implements NotificationDao {
    public NotificationDaoImpl() {
        super(Notification.class);
    }

    @Override
    public List<Notification> getByUserId(Long id) {
        TypedQuery<Notification> query = entityManager.createQuery("FROM Notification WHERE user_id = :id", Notification.class);
        query.setParameter("id", id);

        List<Notification> list = query.getResultList();

        for (Notification n :
                list) {
            Hibernate.initialize(n.getUser());
        }
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
