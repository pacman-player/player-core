package spring.app.dto.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import spring.app.dto.MessageDto;
import spring.app.model.Message;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class MessageDtoDao {
    @PersistenceContext
    private EntityManager entityManager;

    public List<MessageDto> getAllMessageDto(){
        return entityManager.createQuery(
                "SELECT new spring.app.dto.MessageDto(" +
                        "a.id, " +
                        "a.name, " +
                        "a.template" +
                        ") FROM " + Message.class.getName() + " a",
                MessageDto.class
        )
                .getResultList();
    }
}
