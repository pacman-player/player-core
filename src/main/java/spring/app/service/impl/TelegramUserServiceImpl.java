package spring.app.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.app.dao.abstraction.CompanyDao;
import spring.app.dao.abstraction.TelegramUserDao;
import spring.app.dao.abstraction.VisitDao;
import spring.app.model.Company;
import spring.app.model.TelegramUser;
import spring.app.model.Visit;
import spring.app.service.abstraction.TelegramUserService;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class TelegramUserServiceImpl implements TelegramUserService {

    private final TelegramUserDao telegramUserDao;
    private CompanyDao companyDao;
    private VisitDao visitDao;

    public TelegramUserServiceImpl(TelegramUserDao telegramUserDao, CompanyDao companyDao, VisitDao visitDao) {
        this.telegramUserDao = telegramUserDao;
        this.companyDao = companyDao;
        this.visitDao = visitDao;
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

    @Override
    public void deleteTelegramUserById(Long id) {
        TelegramUser telegramUser = telegramUserDao.getById(id);
        Set<Visit> visits = telegramUser.getVisits();
        Set<Company> companies = visits
                .stream()
                .map(v -> companyDao.getById(v.getCompany().getId())).collect(Collectors.toSet());
        visits.clear();
        companies.forEach(c -> c.getVisits().clear());
        telegramUserDao.deleteById(id);
        visits.forEach(v -> visitDao.deleteById(v.getId()));
    }
}
