package spring.app.controller.restController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.app.dto.SongRequest;
import spring.app.dto.SongResponse;
import spring.app.service.abstraction.TelegramService;

import java.io.IOException;

@RestController
@RequestMapping(value = "/api/tlg")
public class TelegramController {
    private final Logger LOGGER = LoggerFactory.getLogger("TelegramController");
    private final TelegramService telegramService;

    public TelegramController(TelegramService telegramService) {
        this.telegramService = telegramService;
    }

    @PostMapping(value = "/song")
    public SongResponse searchRequestedSong(@RequestBody SongRequest songRequest) {
        try {
            return telegramService.getSong(songRequest);
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
            return null;
        }
    }

    @PostMapping(value = "/approve")
    public void approve (@RequestBody SongRequest songRequest) {

    }
}
