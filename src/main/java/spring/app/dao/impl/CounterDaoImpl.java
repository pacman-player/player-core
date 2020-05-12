package spring.app.dao.impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import spring.app.dao.abstraction.CounterDao;
import spring.app.model.Counter;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;

@Repository
public class CounterDaoImpl implements CounterDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void start(String counterType) {
        Counter temp = new Counter(counterType, 0);
        entityManager.persist(temp);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW,
            isolation = Isolation.READ_COMMITTED)
    public int restart(String counterType) {
        Counter temp = new Counter(counterType, 0);
        return entityManager.merge(temp).getValue();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW,
            isolation = Isolation.READ_COMMITTED)
    public void setTo(String counterType, int value) {
        Counter temp = new Counter(counterType, value);
        entityManager.merge(temp);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW,
            isolation = Isolation.READ_COMMITTED)
    public int getValue(String counterType) {
        Counter temp = entityManager.find(Counter.class, counterType, LockModeType.PESSIMISTIC_WRITE);
        if (temp == null) {
            temp = new Counter(counterType, 0);
            entityManager.persist(temp);
        }
        return temp.getValue();
    }
}
