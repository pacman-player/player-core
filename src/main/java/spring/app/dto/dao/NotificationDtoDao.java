package spring.app.dto.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import spring.app.dto.NotificationDto;
import spring.app.model.Notification;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class NotificationDtoDao {
    @PersistenceContext
    private EntityManager entityManager;

    public List<NotificationDto> getAll() {
        return entityManager.createQuery(
                "SELECT new spring.app.dto.NotificationDto(" +
                        "a.id, " +
                        "a.message, " +
                        "a.flag," +
                        "a.user.login" +
                        ") FROM Notification a",
                NotificationDto.class
        )
                .getResultList();
    }


    public NotificationDto getNotificationById(long id) {
        return entityManager.createQuery(
                "SELECT new spring.app.dto.NotificationDto(" +
                        "a.id, " +
                        "a.message, " +
                        "a.flag, " +
                        "a.user.login" +
                        ") FROM " + Notification.class.getName() + " a WHERE a.id = :id",
                NotificationDto.class
        )
                .setParameter("id", id)
                .getSingleResult();
    }
}
