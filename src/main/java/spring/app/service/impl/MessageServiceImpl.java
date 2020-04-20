package spring.app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.app.dao.abstraction.GenreDao;
import spring.app.dao.abstraction.MessageDao;
import spring.app.model.Genre;
import spring.app.model.Message;
import spring.app.service.abstraction.MessageService;

import java.util.List;

@Service
@Transactional
public class MessageServiceImpl extends AbstractService<Message, MessageDao> implements MessageService {

    @Autowired
    public MessageServiceImpl(MessageDao dao) {
        super(dao);
    }

    @Override
    public void addMessage(Message message) {
        dao.save(message);
    }

    @Override
    public List<Message> getAllMessage() {
        return dao.getAll();
    }

    @Override
    public Message getByName(String name) {
        return dao.getByName(name);
    }

    @Override
    public Message getById(Long id) {
        return dao.getById(id);
    }

    @Override
    public void updateMessage(Message message) {
        dao.update(message);
    }

    @Override
    public void deleteMessageById(Long id) {
        dao.deleteById(id);
    }
}
