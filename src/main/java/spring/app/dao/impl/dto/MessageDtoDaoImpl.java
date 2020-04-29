package spring.app.dao.impl.dto;

import org.springframework.stereotype.Repository;
import spring.app.dao.abstraction.dto.MessageDtoDao;
import spring.app.dto.MessageDto;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class MessageDtoDaoImpl implements MessageDtoDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<MessageDto> getAllMessageDto() {
        return entityManager.createQuery(
                "SELECT new spring.app.dto.MessageDto(m.id, m.name, m.template) FROM Message m",
                MessageDto.class
        )
                .getResultList();
    }
}
