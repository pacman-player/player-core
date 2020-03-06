package spring.app.dao.impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import spring.app.dao.abstraction.TelegramUserCompaniesVisitsDao;
import spring.app.model.TelegramUserCompaniesVisits;
import spring.app.model.TelegramUserCompaniesVisitsPrimaryKey;

@Repository
@Transactional(readOnly = true)
public class TelegramUserCompaniesVisitsDaoImpl
        extends AbstractDao<TelegramUserCompaniesVisitsPrimaryKey, TelegramUserCompaniesVisits>
        implements TelegramUserCompaniesVisitsDao {

    TelegramUserCompaniesVisitsDaoImpl() {
        super(TelegramUserCompaniesVisits.class);
    }
}
