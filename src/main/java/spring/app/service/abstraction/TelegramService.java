package spring.app.service.abstraction;

import spring.app.dto.TelegramUser;

import java.io.File;

public interface TelegramService {
    TelegramUser sendSongToBot(TelegramUser telegramUser);

    TelegramUser approve(TelegramUser telegramUser);
}
