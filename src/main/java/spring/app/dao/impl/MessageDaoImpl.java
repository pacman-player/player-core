package spring.app.dao.impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import spring.app.dao.abstraction.MessageDao;
import spring.app.model.Genre;
import spring.app.model.Message;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

@Repository
public class MessageDaoImpl extends AbstractDao<Long, Message> implements MessageDao {
    MessageDaoImpl() {
        super(Message.class);
    }

    @Override
    public Message getByName(String name) {
        TypedQuery<Message> query = entityManager.createQuery("SELECT u FROM Message u WHERE u.name = :name", Message.class);
        query.setParameter("name", name);
        Message message;
        try {
            message = query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
        return message;
    }
}
