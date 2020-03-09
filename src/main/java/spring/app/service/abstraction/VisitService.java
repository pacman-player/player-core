package spring.app.service.abstraction;

import spring.app.model.Company;
import spring.app.model.TelegramUser;

public interface VisitService {

    void registerVisit(TelegramUser telegramUser, Company company);
}
