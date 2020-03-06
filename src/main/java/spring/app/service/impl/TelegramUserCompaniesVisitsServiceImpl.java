package spring.app.service.impl;

import org.springframework.stereotype.Service;
import spring.app.dao.abstraction.TelegramUserCompaniesVisitsDao;
import spring.app.model.TelegramUserCompaniesVisits;
import spring.app.service.abstraction.TelegramUserCompaniesVisitsService;

import javax.transaction.Transactional;

@Service
@Transactional
public class TelegramUserCompaniesVisitsServiceImpl implements TelegramUserCompaniesVisitsService {

    private TelegramUserCompaniesVisitsDao telegramUserCompaniesVisitsDao;

    public TelegramUserCompaniesVisitsServiceImpl(TelegramUserCompaniesVisitsDao telegramUserCompaniesVisitsDao) {
        this.telegramUserCompaniesVisitsDao = telegramUserCompaniesVisitsDao;
    }

    @Override
    public void addVisit(TelegramUserCompaniesVisits telegramUserCompaniesVisits) {
        telegramUserCompaniesVisitsDao.save(telegramUserCompaniesVisits);
    }
}
