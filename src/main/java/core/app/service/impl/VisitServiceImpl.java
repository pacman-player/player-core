package core.app.service.impl;

import core.app.model.Company;
import org.springframework.stereotype.Service;
import core.app.dao.abstraction.VisitDao;
import core.app.model.TelegramUser;
import core.app.model.Visit;
import core.app.service.abstraction.VisitService;

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
