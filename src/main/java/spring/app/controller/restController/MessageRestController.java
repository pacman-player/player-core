package spring.app.controller.restController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import spring.app.dto.GenreDto;
import spring.app.dto.MessageDto;
import spring.app.dto.UserDto;
import spring.app.model.Genre;
import spring.app.model.Message;
import spring.app.model.User;
import spring.app.service.abstraction.MessageService;

import javax.swing.*;
import java.util.List;

import static org.springframework.security.core.context.SecurityContextHolder.getContext;

@RestController
@RequestMapping("/api/v1/admin/message")
public class MessageRestController {

    private final MessageService messageService;

    @Autowired
    public MessageRestController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping(value = "/all_messages")
    public List<Message> getAllMessage() {
        return messageService.getAllMessage();
    }

    @PostMapping(value = "/add_message")
    public void addMessage(@RequestBody MessageDto messageDto) throws InterruptedException {
        Message message = new Message(messageDto.getName(), messageDto.getTemplate());
        messageService.addMessage(message);
    }

    @PutMapping(value = "/update_message")
    public void updateGenre(@RequestBody MessageDto messageDto) throws InterruptedException {
        Message message = messageService.getById(messageDto.getId());
        message.setName(messageDto.getName());
        message.setTemplate(messageDto.getTemplate());
        messageService.updateMessage(message);
    }

    @DeleteMapping(value = "/delete_message")
    public void deleteGenre(@RequestBody Long id) throws InterruptedException {
        Message message = messageService.getById(id);
        messageService.deleteMessageById(id);
    }
}
