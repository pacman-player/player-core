package core.app.dao.abstraction;

import core.app.model.TelegramUser;

public interface TelegramUserDao extends GenericDao<Long, TelegramUser> {

    boolean isTelegramUserExists(Long telegramUserId);
}
