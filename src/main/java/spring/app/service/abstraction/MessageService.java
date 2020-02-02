package spring.app.service.abstraction;

import spring.app.model.Genre;
import spring.app.model.Message;

import java.util.List;

public interface MessageService {

    void addMessage(Message message);

    List<Message> getAllMessage();

    Message getByName(String name);

    Message getById(Long id);

    void updateMessage(Message message);

    void deleteMessageById(Long id);
}
