package spring.app.dao.abstraction;

import spring.app.model.Visit;

import java.util.List;

public interface VisitDao extends GenericDao<Visit.VisitPK, Visit> {

    List<Visit> getAllByCompanyId(Long id);

    List<Visit> getAllByTelegramUserId(Long id);

    List<Visit> getAllByTelegramUserIdAndCompanyId(Long telegramUserId, Long companyId);
}
