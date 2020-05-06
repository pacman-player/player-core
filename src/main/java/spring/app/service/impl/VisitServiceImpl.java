package spring.app.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.app.dao.abstraction.VisitDao;
import spring.app.model.Company;
import spring.app.model.TelegramUser;
import spring.app.model.Visit;
import spring.app.service.abstraction.VisitService;

import java.util.List;

@Service
public class VisitServiceImpl extends AbstractServiceImpl<Visit.VisitPK, Visit, VisitDao> implements VisitService {


    public VisitServiceImpl(VisitDao dao) {
        super(dao);
    }

    @Override
    @Transactional
    public void addVisit(TelegramUser telegramUser, Company company) {
        dao.save(new Visit(telegramUser, company));
    }

    @Override
    public List<Visit> getAllByCompanyId(Long id) {
        return dao.getAllByCompanyId(id);
    }

    @Override
    public List<Visit> getAllByTelegramUserId(Long id) {
        return dao.getAllByTelegramUserId(id);
    }

    @Override
    public List<Visit> getAllByTelegramUserIdAndCompanyId(Long telegramUserId, Long companyId) {
        return dao.getAllByTelegramUserIdAndCompanyId(telegramUserId, companyId);
    }
}
