package spring.app.dao.impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import spring.app.dao.abstraction.CounterDao;
import spring.app.model.Counter;
import spring.app.model.CounterType;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;

@Repository
public class CounterDaoImpl implements CounterDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void start(CounterType counterType) {
        Counter temp = new Counter(counterType, 0);
        entityManager.persist(temp);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW,
            isolation = Isolation.READ_COMMITTED)
    public Counter restart(CounterType counterType) {
        Counter temp = new Counter(counterType, 0);
        return entityManager.merge(temp);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW,
            isolation = Isolation.READ_COMMITTED)
    public void setTo(CounterType counterType, int value) {
        Counter temp = new Counter(counterType, value);
        entityManager.merge(temp);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW,
            isolation = Isolation.READ_COMMITTED)
    public int getValue(CounterType counterType) {
        Counter temp = entityManager.find(Counter.class, counterType, LockModeType.PESSIMISTIC_READ);
        return temp.getValue();
    }
}
