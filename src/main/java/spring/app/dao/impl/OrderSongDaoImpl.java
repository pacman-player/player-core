package spring.app.dao.impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import spring.app.dao.abstraction.OrderSongDao;
import spring.app.model.OrderSong;

import java.sql.Timestamp;

@Repository
@Transactional
public class OrderSongDaoImpl extends AbstractDao<Long, OrderSong> implements OrderSongDao {

    OrderSongDaoImpl() {
        super(OrderSong.class);
    }


    @Override
    public long getSongOrdersByCompanyIdAndPeriod(Long id, Timestamp after) {
        return entityManager.createQuery("SELECT COUNT(o) FROM OrderSong o WHERE o.company.id = :id AND o.timestamp > :after", Long.class)
                .setParameter("id", id)
                .setParameter("after", after)
                .getSingleResult();
    }

    @Override
    public long getSongOrdersByCompanyIdAndTimeRange(Long id, Timestamp start, Timestamp end) {
           return entityManager.createQuery("SELECT COUNT(o) FROM OrderSong o WHERE o.company.id = :id AND o.timestamp BETWEEN :start AND :end", Long.class)
                   .setParameter("id", id)
                   .setParameter("start", start)
                   .setParameter("end", end)
                   .getSingleResult();
    }

    @Override
    public long countAll(Long id) {
        return entityManager.createQuery("SELECT COUNT(o) FROM OrderSong o WHERE o.company.id = :id", Long.class)
                .setParameter("id", id)
                .getSingleResult();
    }
}
