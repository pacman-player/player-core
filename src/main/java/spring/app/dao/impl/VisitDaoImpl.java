package spring.app.dao.impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import spring.app.dao.abstraction.VisitDao;
import spring.app.model.Visit;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class VisitDaoImpl extends AbstractDao<Visit.VisitPK, Visit> implements VisitDao {

    @PersistenceContext
    private EntityManager entityManager;

    VisitDaoImpl() {
        super(Visit.class);
    }

    @Override
    public List<Visit> getAllByCompanyId(Long id) {
        List<Visit> visits = new ArrayList<>();
        try {
            visits = entityManager.
                    createQuery("FROM Visit WHERE visitPK.company.id = :id", Visit.class)
                    .setParameter("id", id)
                    .getResultList();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return visits;
    }

    @Override
    public List<Visit> getAllByTelegramUserId(Long id) {
        List<Visit> visits = new ArrayList<>();
        try {
            visits = entityManager.
                    createQuery("FROM Visit WHERE visitPK.telegramUser.id = :id", Visit.class)
                    .setParameter("id", id)
                    .getResultList();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return visits;
    }

    @Override
    public List<Visit> getAllByTelegramUserIdAndCompanyId(Long telegramUserId, Long companyId) {
        List<Visit> visits = new ArrayList<>();
        try {
            visits = entityManager.
                    createQuery("FROM Visit WHERE visitPK.telegramUser.id = :telegramUserId AND visitPK.company.id = :companyId", Visit.class)
                    .setParameter("telegramUserId", telegramUserId)
                    .setParameter("companyId", companyId)
                    .getResultList();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return visits;
    }
}
