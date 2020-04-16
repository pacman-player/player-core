package spring.app.dto.mapping;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import spring.app.dto.AuthorDto;
import spring.app.dto.NotificationDto;
import spring.app.model.Author;
import spring.app.model.Notification;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Transactional
@Repository
public class NotificationDtoMapping {
    @PersistenceContext
    EntityManager entityManager;

    public List<NotificationDto> getAll() {
        return entityManager.createQuery(
                "SELECT new spring.app.dto.NotificationDto(" +
                        "a.id, " +
                        "a.message, " +
                        "a.flag," +
                        "a.user.login" +
                        ") FROM " + Notification.class.getName() + " a",
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
