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
public class MessageServiceImpl extends AbstractServiceImpl<Long, Message, MessageDao> implements MessageService {

    private final MessageDtoDao messageDtoDao;

    @Autowired
    public MessageServiceImpl(MessageDao dao, MessageDtoDao messageDtoDao) {
        super(dao);
        this.messageDtoDao = messageDtoDao;
    }

    @Override
    public List<MessageDto> getAllMessageDto() {
        return messageDtoDao.getAllMessageDto();
    }

    @Override
    public Message getByName(String name) {
        return dao.getByName(name);
    }

}
