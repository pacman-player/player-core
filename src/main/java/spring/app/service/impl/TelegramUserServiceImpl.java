package spring.app.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.app.dao.abstraction.TelegramUserDao;
import spring.app.model.TelegramUser;
import spring.app.service.abstraction.TelegramUserService;

@Service
@Transactional
public class TelegramUserServiceImpl implements TelegramUserService {

    private final TelegramUserDao telegramUserDao;

    public TelegramUserServiceImpl(TelegramUserDao telegramUserDao) {
        this.telegramUserDao = telegramUserDao;
    }

    @Override
    public boolean isTelegramUserExists(Long telegramUserId) {
        return telegramUserDao.isTelegramUserExists(telegramUserId);
    }

    @Override
    public void addTelegramUser(TelegramUser telegramUser) {
        if (!isTelegramUserExists(telegramUser.getId())) {
            telegramUserDao.save(telegramUser);
        }
    }
}
