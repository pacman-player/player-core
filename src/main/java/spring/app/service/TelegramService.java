package spring.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import spring.app.dto.TelegramUser;

import java.io.File;

@Service
public class TelegramService {

    private final RestTemplate restTemplate;

    @Autowired
    public TelegramService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public TelegramUser sendSongToBot (TelegramUser telegramUser) {
        String URL = "http://localhost:8888/bot/song";
        File file = new File("D:\\pacman\\We Wish You A Merry Christmas.mp3");
        telegramUser.setTrack(file);
        telegramUser.setSongId(25122019l);
        return restTemplate.postForObject(URL, telegramUser, TelegramUser.class);
    }

    public TelegramUser approve (TelegramUser telegramUser) {
        String URL = "http://localhost:8888/bot/approve";
        return restTemplate.postForObject(URL, telegramUser, TelegramUser.class);
    }
}
