package spring.app.dao.impl;

import com.vk.api.sdk.exceptions.ApiUploadException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import spring.app.dao.abstraction.OrderSongDao;
import spring.app.model.OrderSong;

import javax.persistence.Query;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

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

    @Override
    public List<Long> getAllByCompanyId(Long companyId) {

        Query q = entityManager.createNativeQuery(
                "SELECT osng.id " +
                        "FROM order_song osng " +
                        "WHERE osng.company_id = ?"
        );
        q.setParameter(1, companyId);
        List idsList = q.getResultList();
        List<Long> resultList = new ArrayList<>();
        for (Object num : idsList
        ) {
            resultList.add(((BigInteger) num).longValue());
        }
        return resultList;
    }

    @Override
    public void bulkRemoveOrderSongByCompany(Long companyId) {
        entityManager.createQuery("DELETE FROM OrderSong o WHERE o.company.id = :companyId")
                .setParameter("companyId", companyId)
                .executeUpdate();
        entityManager.flush();
    }
}
