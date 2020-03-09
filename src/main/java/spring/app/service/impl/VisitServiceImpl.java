package spring.app.service.impl;

import org.springframework.stereotype.Service;
import spring.app.dao.abstraction.VisitDao;
import spring.app.dao.impl.CompanyDaoImpl;
import spring.app.dao.impl.TelegramUserDaoImpl;
import spring.app.dto.VisitDto;
import spring.app.model.Company;
import spring.app.model.TelegramUser;
import spring.app.model.Visit;
import spring.app.model.VisitPrimaryKey;
import spring.app.service.abstraction.VisitService;

import javax.transaction.Transactional;
import java.sql.Timestamp;

@Service
@Transactional
public class VisitServiceImpl implements VisitService {

    private TelegramUserDaoImpl telegramUserDao;
    private CompanyDaoImpl companyDao;
    private VisitDao visitDao;

    public VisitServiceImpl(TelegramUserDaoImpl telegramUserDao,
                            CompanyDaoImpl companyDao,
                            VisitDao visitDao) {
        this.telegramUserDao = telegramUserDao;
        this.companyDao = companyDao;
        this.visitDao = visitDao;
    }

    /**
     * Метод регистрирует в нашей базе данных
     * пользователя Telegram и факт посещения этим пользователем заведения
     * @param visitDto
     */
    @Override
    public void registerTelegramUserAndVisit(VisitDto visitDto) {
        TelegramUser telegramUser = new TelegramUser(visitDto.getTelegramUserDto());
        if (!telegramUserDao.isTelegramUserExists(telegramUser.getId())) {
            telegramUserDao.save(telegramUser);
        }
        Company company = companyDao.getById(visitDto.getCompanyId());
        VisitPrimaryKey primaryKey = new VisitPrimaryKey(
                telegramUser, company, new Timestamp(System.currentTimeMillis())
        );
        Visit visit = new Visit(primaryKey);
        visitDao.save(visit);
    }
}
