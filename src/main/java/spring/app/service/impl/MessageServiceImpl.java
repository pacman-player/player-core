package spring.app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.app.dao.abstraction.MessageDao;
import spring.app.dao.abstraction.dto.MessageDtoDao;
import spring.app.dto.MessageDto;
import spring.app.model.Message;
import spring.app.service.abstraction.MessageService;

import java.util.List;

@Service
@Transactional
public class MessageServiceImpl implements MessageService {

    private final MessageDao messageDao;
    private final MessageDtoDao messageDtoDao;

    @Autowired
    public MessageServiceImpl(MessageDao messageDao, MessageDtoDao messageDtoDao) {
        this.messageDao = messageDao;
        this.messageDtoDao = messageDtoDao;
    }

    @Override
    public void addMessage(Message message) {
        messageDao.save(message);
    }

    @Override
    public List<Message> getAllMessage() {
        return messageDao.getAll();
    }

    @Override
    public List<MessageDto> getAllMessageDto() {
        return messageDtoDao.getAllMessageDto();
    }

    @Override
    public Message getByName(String name) {
        return messageDao.getByName(name);
    }

    @Override
    public Message getById(Long id) {
        return messageDao.getById(id);
    }

    @Override
    public void updateMessage(Message message) {
        messageDao.update(message);
    }

    @Override
    public void deleteMessageById(Long id) {
        messageDao.deleteById(id);
    }
}
