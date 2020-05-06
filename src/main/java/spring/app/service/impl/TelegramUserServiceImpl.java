package spring.app.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.app.dao.abstraction.TelegramUserDao;
import spring.app.model.TelegramUser;
import spring.app.service.abstraction.TelegramUserService;

import java.util.Optional;

@Service
public class TelegramUserServiceImpl extends AbstractServiceImpl<Long, TelegramUser, TelegramUserDao> implements TelegramUserService {

    public TelegramUserServiceImpl(TelegramUserDao dao) {
        super(dao);
    }

    @Override
    public boolean isExistById(Long telegramUserId) {
        return dao.isTelegramUserExists(telegramUserId);
    }

    @Override
    public Optional<TelegramUser> getTelegramUserById(Long id) {
        return Optional.ofNullable(dao.getById(id));
    }

    @Override
    @Transactional
    public void save(TelegramUser telegramUser) {
        if (!this.isExistById(telegramUser.getId())) {
            dao.save(telegramUser);
        }
    }
}
