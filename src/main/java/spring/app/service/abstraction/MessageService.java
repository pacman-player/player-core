package spring.app.service.abstraction;

import spring.app.dto.MessageDto;
import spring.app.model.Message;
import java.util.List;

public interface MessageService extends GenericService<Long, Message> {

    List<MessageDto> getAllMessageDto();

    Message getByName(String name);
}
