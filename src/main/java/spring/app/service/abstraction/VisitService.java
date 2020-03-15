package spring.app.service.abstraction;

import spring.app.model.Company;
import spring.app.model.TelegramUser;
import spring.app.model.Visit;

import java.util.List;

public interface VisitService {

    void addVisit(TelegramUser telegramUser, Company company);

    List<Visit> getAllByCompanyId(Long id);

    List<Visit> getAllByTelegramUserId(Long id);

    List<Visit> getAllByTelegramUserIdAndCompanyId(Long telegramUserId, Long companyId);
}
