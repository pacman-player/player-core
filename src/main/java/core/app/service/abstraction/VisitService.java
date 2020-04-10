package core.app.service.abstraction;

import core.app.model.Company;
import core.app.model.TelegramUser;
import core.app.model.Visit;

import java.util.List;

public interface VisitService {

    void addVisit(TelegramUser telegramUser, Company company);

    List<Visit> getAllByCompanyId(Long id);

    List<Visit> getAllByTelegramUserId(Long id);

    List<Visit> getAllByTelegramUserIdAndCompanyId(Long telegramUserId, Long companyId);
}
