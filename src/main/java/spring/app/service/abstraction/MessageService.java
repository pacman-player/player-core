package spring.app.service.abstraction;

import spring.app.model.Message;

public interface MessageService extends GenericService<Long, Message> {

    Message getByName(String name);
}
