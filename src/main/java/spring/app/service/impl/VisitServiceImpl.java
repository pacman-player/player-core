package spring.app.service.impl;

import org.springframework.stereotype.Service;
import spring.app.dao.abstraction.VisitDao;
import spring.app.model.Company;
import spring.app.model.TelegramUser;
import spring.app.model.Visit;
import spring.app.service.abstraction.VisitService;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class VisitServiceImpl implements VisitService {

    private VisitDao visitDao;

    public VisitServiceImpl(VisitDao visitDao) {
        this.visitDao = visitDao;
    }

    @Override
    public void addVisit(TelegramUser telegramUser, Company company) {
        visitDao.save(new Visit(telegramUser, company));
    }

    @Override
    public List<Visit> getAllByCompanyId(Long id) {
        return visitDao.getAllByCompanyId(id);
    }

    @Override
    public List<Visit> getAllByTelegramUserId(Long id) {
        return visitDao.getAllByTelegramUserId(id);
    }

    @Override
    public List<Visit> getAllByTelegramUserIdAndCompanyId(Long telegramUserId, Long companyId) {
        return visitDao.getAllByTelegramUserIdAndCompanyId(telegramUserId, companyId);
    }
}
