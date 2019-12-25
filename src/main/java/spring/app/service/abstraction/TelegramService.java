package spring.app.service.abstraction;

import spring.app.dto.SongRequest;
import spring.app.dto.SongResponce;

import java.io.IOException;

public interface TelegramService {
    SongResponce getSong(SongRequest telegramUser) throws IOException;

}
