package spring.app.controller.restController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import spring.app.dto.MessageDto;
import spring.app.model.Message;
import spring.app.service.abstraction.MessageService;

import java.util.List;

@RestController
@RequestMapping("/api/admin/message")
public class MessageRestController {
    private final static Logger LOGGER = LoggerFactory.getLogger(MessageRestController.class);
    private final MessageService messageService;

    @Autowired
    public MessageRestController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping(value = "/all_messages")
    public List<Message> getAllMessage() {
        List <Message> list = messageService.getAllMessage();
        return list;
    }

    @PostMapping(value = "/add_message")
    public void addMessage(@RequestBody MessageDto messageDto) {
        LOGGER.info("POST request '/add_message'");
        Message message = new Message(messageDto.getName(), messageDto.getTemplate());
        messageService.addMessage(message);
        LOGGER.info("Added Message with name = {}", messageDto.getName());
    }

    @PutMapping(value = "/update_message")
    public void updateGenre(@RequestBody MessageDto messageDto) throws InterruptedException {
        // метод действительно выбрасывает это исключение?
        LOGGER.info("PUT request '/update_message'");
        Message message = messageService.getById(messageDto.getId());
        message.setName(messageDto.getName());
        message.setTemplate(messageDto.getTemplate());
        messageService.updateMessage(message);
        LOGGER.info("Updated Message with name = {}", messageDto.getName());
    }

    @DeleteMapping(value = "/delete_message")
    public void deleteGenre(@RequestBody Long id) throws InterruptedException {
        // метод действительно выбрасывает это исключение?
        LOGGER.info("DELETE request '/delete_message/{}'", id);
        Message message = messageService.getById(id);
        messageService.deleteMessageById(id);
        LOGGER.info("Deleted Message with name = {}", message.getName());
    }
}
