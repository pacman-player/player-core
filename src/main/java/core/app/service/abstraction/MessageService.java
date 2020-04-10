package core.app.service.abstraction;

import core.app.model.Message;

import java.util.List;

public interface MessageService {

    void addMessage(Message message);

    List<Message> getAllMessage();

    Message getByName(String name);

    Message getById(Long id);

    void updateMessage(Message message);

    void deleteMessageById(Long id);
}
