package spring.app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.app.dao.abstraction.MessageDao;
import spring.app.model.Message;
import spring.app.service.abstraction.MessageService;

import java.util.List;

@Service
@Transactional
public class MessageServiceImpl extends AbstractServiceImpl<Message, MessageDao, Long> implements MessageService {

    @Autowired
    public MessageServiceImpl(MessageDao dao) {
        super(dao);
    }

    @Override
    public Message getByName(String name) {
        return dao.getByName(name);
    }

}
