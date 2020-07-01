package spring.app.dao.impl;

import org.hibernate.Hibernate;
import org.springframework.stereotype.Repository;
import spring.app.dao.abstraction.VisitDao;
import spring.app.model.Visit;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class VisitDaoImpl extends AbstractDao<Visit.VisitPK, Visit> implements VisitDao {

    @PersistenceContext
    private EntityManager entityManager;

    VisitDaoImpl() {
        super(Visit.class);
    }

    @Override
    public List<Visit> getAllByCompanyId(Long id) {
        List<Visit> list = entityManager.
                createQuery("FROM Visit WHERE visitPK.company.id = :id", Visit.class)
                .setParameter("id", id)
                .getResultList();
        for (Visit v :
                list) {
            initLazyFields(v);
        }
        return list;
    }

    @Override
    public List<Visit> getAllByTelegramUserId(Long id) {
        List<Visit> list = entityManager.
                createQuery("FROM Visit WHERE visitPK.telegramUser.id = :id", Visit.class)
                .setParameter("id", id)
                .getResultList();
        for (Visit v :
                list) {
            initLazyFields(v);
        }
        return list;
    }

    @Override
    public List<Visit> getAllByTelegramUserIdAndCompanyId(Long telegramUserId, Long companyId) {
        List<Visit> list = entityManager.
                createQuery("FROM Visit WHERE visitPK.telegramUser.id = :telegramUserId AND visitPK.company.id = :companyId", Visit.class)
                .setParameter("telegramUserId", telegramUserId)
                .setParameter("companyId", companyId)
                .getResultList();
        for (Visit v :
                list) {
            initLazyFields(v);
        }
        return list;
    }

    private void initLazyFields(Visit v) {
        Hibernate.initialize(v.getTelegramUser());
        Hibernate.initialize(v.getCompany());
    }
}
