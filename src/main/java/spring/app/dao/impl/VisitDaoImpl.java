package spring.app.dao.impl;

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
        return entityManager.
                createQuery("FROM Visit WHERE visitPK.company.id = :id", Visit.class)
                .setParameter("id", id)
                .getResultList();
    }

    @Override
    public List<Visit> getAllByTelegramUserId(Long id) {
        return entityManager.
                createQuery("FROM Visit WHERE visitPK.telegramUser.id = :id", Visit.class)
                .setParameter("id", id)
                .getResultList();
    }

    @Override
    public List<Visit> getAllByTelegramUserIdAndCompanyId(Long telegramUserId, Long companyId) {
        return entityManager.
                createQuery("FROM Visit WHERE visitPK.telegramUser.id = :telegramUserId AND visitPK.company.id = :companyId", Visit.class)
                .setParameter("telegramUserId", telegramUserId)
                .setParameter("companyId", companyId)
                .getResultList();
    }
}
