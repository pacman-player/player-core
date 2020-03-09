package spring.app.service.impl;

import org.springframework.stereotype.Service;
import spring.app.dao.abstraction.VisitDao;
import spring.app.model.Company;
import spring.app.model.TelegramUser;
import spring.app.model.Visit;
import spring.app.service.abstraction.VisitService;

import javax.transaction.Transactional;

@Service
@Transactional
public class VisitServiceImpl implements VisitService {

    private VisitDao visitDao;

    public VisitServiceImpl(VisitDao visitDao) {
        this.visitDao = visitDao;
    }

    /**
     * Метод регистрирует в нашей базе данных
     * факт посещения этим пользователем заведения
     * @param telegramUser
     * @param company
     */
    @Override
    public void registerVisit(TelegramUser telegramUser, Company company) {
        visitDao.save(new Visit(telegramUser, company));
    }
}
