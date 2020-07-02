package spring.app.dao.impl;

import org.hibernate.Hibernate;
import org.springframework.stereotype.Repository;
import spring.app.dao.abstraction.SongStatisticDao;
import spring.app.model.SongStatistic;

import javax.persistence.NoResultException;
import javax.transaction.Transactional;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Repository
@Transactional
public class SongStatisticDaoImpl extends AbstractDao<Long, SongStatistic> implements SongStatisticDao {

    SongStatisticDaoImpl() {
        super(SongStatistic.class);
    }

    @Override
    public Long getOrderCountBySongName(String songName) {
        try {
            return (Long) entityManager.createQuery("SELECT orderCount FROM SongStatistic WHERE songName = " + songName)
                    .getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

    @Override
    public List<SongStatistic> getSortedTopListForDay(int songsCount) {
        try {
            List<SongStatistic> list = entityManager.createQuery("FROM SongStatistic where orderDate = " +
                    ":orderDate order by orderCount desc")
                    .setParameter("orderDate", Date.valueOf(LocalDate.now()))
                    .setMaxResults(songsCount)
                    .getResultList();
            for (SongStatistic ss :
                    list) {
                initLazyFields(ss);
            }
            return list;
        } catch (NoResultException ex) {
            return null;
        }
    }

    @Override
    public List<SongStatistic> getSortedTopListForRange(int songsCount, Date orderDate) {
        try {
            List<SongStatistic> list = entityManager.createQuery("FROM SongStatistic where orderDate >= " +
                    ":startDate and <= :limitDate order by orderCount desc")
                    .setParameter("orderDate", Date.valueOf(LocalDate.now()))
                    .setParameter("orderDate", orderDate)
                    .setMaxResults(songsCount)
                    .getResultList();
            for (SongStatistic ss :
                    list) {
                initLazyFields(ss);
            }
            return list;
        } catch (NoResultException ex) {
            return null;
        }
    }

    @Override
    public SongStatistic getSongStatisticByNameAndDate(String songName, Date orderDate) {
        try {
            SongStatistic songStatistic = (SongStatistic) entityManager.createQuery("FROM SongStatistic where songName = :songName and " +
                    "orderDate = :orderDate")
                    .setParameter("songName", songName)
                    .setParameter("orderDate", orderDate)
                    .getSingleResult();
            initLazyFields(songStatistic);
            return songStatistic;
        } catch (NoResultException ex) {
            return null;
        }
    }

    private void initLazyFields(SongStatistic ss) {
        Hibernate.initialize(ss.getSong());
    }
}
