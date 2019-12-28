package spring.app.controller.restController;

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

    private final TelegramService telegramService;

    public TelegramController(TelegramService telegramService) {
        this.telegramService = telegramService;
    }

    @PostMapping(value = "/song")
    public SongResponse searchRequestedSong(@RequestBody SongRequest songRequest) throws IOException {
        return telegramService.getSong(songRequest);
    }

    @PostMapping(value = "/approve")
    public void approve(@RequestBody SongRequest songRequest) {

    }
}
