package spring.app.service.abstraction;

import spring.app.model.TelegramUser;

import java.util.Optional;

public interface TelegramUserService extends GenericService<Long, TelegramUser> {

    Optional<TelegramUser> getTelegramUserById(Long id);

}
