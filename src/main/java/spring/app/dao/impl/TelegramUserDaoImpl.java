package spring.app.dao.impl;

import org.springframework.stereotype.Repository;
import spring.app.dao.abstraction.TelegramUserDao;
import spring.app.model.TelegramUser;

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
