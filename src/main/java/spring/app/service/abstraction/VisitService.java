package spring.app.service.abstraction;

import spring.app.model.Company;
import spring.app.model.TelegramUser;
import spring.app.model.Visit;
//import spring.app.model.VisitPK;

import java.sql.Timestamp;

public interface VisitService {

    void addVisit(TelegramUser telegramUser, Company company);

//    void addVisit(VisitPK visitPK);

    void addVisit(Visit visit);

//    void deleteVisitById(Timestamp timestamp);
}
