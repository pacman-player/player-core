package spring.app.controller.restController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.app.dto.TelegramUser;
import spring.app.service.impl.TelegramServiceImpl;

@RestController
@RequestMapping(value = "/api/tlg")
public class TelegramController {

    private final TelegramServiceImpl telegramService;

    @Autowired
    public TelegramController(TelegramServiceImpl telegramService) {
        this.telegramService = telegramService;
    }

    @PostMapping(value = "/song")
    public void add(@RequestBody TelegramUser tlgUser) {
        telegramService.sendSongToBot(tlgUser);
    }

    @PostMapping(value = "/approve")
    public void approve (@RequestBody TelegramUser tlgUser) {
        telegramService.approve(tlgUser);
    }
}
