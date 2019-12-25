package spring.app.service.impl;

import org.springframework.stereotype.Service;
import spring.app.dto.SongRequest;
import spring.app.dto.SongResponce;
import spring.app.service.abstraction.TelegramService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
public class TelegramServiceImpl implements TelegramService {

    @Override
    public SongResponce getSong(SongRequest songRequest) throws IOException {


        byte[] bytes = Files.readAllBytes(Paths.get("D:\\pacman\\We Wish You A Merry Christmas.mp3"));
        SongResponce songResponce = new SongResponce(songRequest.getChatId(), 25122019l, bytes, "We Wish You A Merry Christmas");

        return songResponce;
    }
}
