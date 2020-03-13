package spring.app.service.impl;

import org.springframework.stereotype.Service;
import spring.app.dao.abstraction.VisitDao;
import spring.app.model.Company;
import spring.app.model.TelegramUser;
import spring.app.model.Visit;
//import spring.app.model.VisitPK;
import spring.app.service.abstraction.VisitService;

import javax.transaction.Transactional;

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

//    @Override
//    public void addVisit(VisitPK visitPK) {
//        visitDao.save(new Visit(visitPK));
//    }

    @Override
    public void addVisit(Visit visit) {
        visitDao.save(visit);
    }

//    @Override
//    public void deleteVisitById(VisitPK visitPK) {
//        visitDao.deleteById(visitPK);
//    }
}
