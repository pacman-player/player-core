package core.app.dao.impl;

import core.app.model.TelegramUser;
import org.springframework.stereotype.Repository;
import core.app.dao.abstraction.TelegramUserDao;

@Repository
public class TelegramUserDaoImpl extends AbstractDao<Long, TelegramUser> implements TelegramUserDao {

    public TelegramUserDaoImpl() {
        super(TelegramUser.class);
    }

    @Override
    public boolean isTelegramUserExists(Long telegramUserId) {
        return (Long) entityManager.createQuery(
                "SELECT COUNT(tu.id) FROM TelegramUser tu WHERE tu.id = :id")
                .setParameter("id", telegramUserId)
                .getSingleResult() > 0;
    }
}
