package spring.app.service.abstraction;

import spring.app.model.TelegramUser;

public interface TelegramUserService {

    boolean isTelegramUserExists(Long telegramUserId);

    void addTelegramUser(TelegramUser telegramUser);

    void deleteTelegramUserById(Long id);
}
