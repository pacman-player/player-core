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
                .setParameter("id", id).getSingleResult();
    }

    /*Select ...
From Keywords As K
    Left Join NegativeKeywords As NK
        On NK.EntityKey = K.EntityKey
    Left Join RequiredKeywords As RK
        On RK.EntityKey = K.EntityKey
Where NK.EntityKey Is Null
    And (
        Not Exists  (
                    Select 1
                    From RequiredKeywords As RK1
                    Where RK1.EntityKey = K.EntityKey
                    )
        Or RK.EntityKey Is Not Null
        )*/
    @Override
    public List<Long> getAllByCompanyId(Long companyId) {

        Query q = entityManager.createNativeQuery(
                "SELECT osng.id " +
                        "FROM order_song osng " +
                        "WHERE osng.company_id = ?"
        );
        q.setParameter(1, companyId);
        List stepsList = q.getResultList();
        List<Long> resultList = new ArrayList<>();
        for (Object num: stepsList
        ) {
            resultList.add(((BigInteger)num).longValue());
        }
        return resultList;

        /*return entityManager.createQuery(
                "SELECT osng.id " +
                        "FROM OrderSong AS osng " +
                        "LEFT JOIN Company AS cmp " +
                        "ON osng.company = cmp " +
                        "WHERE cmp.id = :companyId",
                Long.class)
                .setParameter("companyId", companyId)
                .getResultList();*/
    }

    @Override

    public void bulkRemoveOrderSongByCompany(Long companyId) {
        List<Long> songIds = getAllByCompanyId(companyId);
        for (Long song : songIds
        ) {
            super.deleteById(song);
        }
        entityManager.flush();
    }
}
