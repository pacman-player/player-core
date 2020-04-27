package spring.app.dao.impl.dto;

import org.springframework.stereotype.Repository;
import spring.app.dao.abstraction.dto.NotificationDtoDao;
import spring.app.dto.NotificationDto;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class NotificationDtoDaoImpl implements NotificationDtoDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<NotificationDto> getAll() {
        return entityManager.createQuery(
                "SELECT new spring.app.dto.NotificationDto(n.id, n.message, n.flag, n.user.login) FROM Notification n",
                NotificationDto.class
        )
                .getResultList();
    }

    @Override
    public NotificationDto getNotificationById(long id) {
        return entityManager.createQuery(
                "SELECT new spring.app.dto.NotificationDto(n.id, n.message, n.flag, n.user.login) " +
                        "FROM Notification n WHERE n.id = :id",
                NotificationDto.class
        )
                .setParameter("id", id)
                .getSingleResult();
    }
}
