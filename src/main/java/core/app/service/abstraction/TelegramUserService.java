package core.app.service.abstraction;

import core.app.model.TelegramUser;

import java.util.Optional;

public interface TelegramUserService {

    boolean isTelegramUserExists(Long telegramUserId);

    Optional<TelegramUser> getTelegramUserById(Long id);

    void addTelegramUser(TelegramUser telegramUser);

    void deleteById(Long id);
}
