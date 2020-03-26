package spring.app.dao.abstraction;

import spring.app.model.TelegramUser;

public interface TelegramUserDao extends GenericDao<Long, TelegramUser> {

    boolean isTelegramUserExists(Long telegramUserId);
}
