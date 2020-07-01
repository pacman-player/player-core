package spring.app.dao.impl;

import org.springframework.stereotype.Repository;
import spring.app.dao.abstraction.SongStatisticDao;
import spring.app.model.SongStatistic;

import javax.persistence.NoResultException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Repository
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
        return entityManager.createQuery("FROM SongStatistic where orderDate = " +
                ":orderDate order by orderCount desc")
                .setParameter("orderDate", Date.valueOf(LocalDate.now()))
                .setMaxResults(songsCount)
                .getResultList();
        } catch (NoResultException ex) {
            return null;
        }
    }

    @Override
    public List<SongStatistic> getSortedTopListForRange(int songsCount, Date orderDate) {
        try {
            return entityManager.createQuery("FROM SongStatistic where orderDate >= " +
                    ":startDate and <= :limitDate order by orderCount desc")
                    .setParameter("orderDate", Date.valueOf(LocalDate.now()))
                    .setParameter("orderDate", orderDate)
                    .setMaxResults(songsCount)
                    .getResultList();
        } catch (NoResultException ex) {
            return null;
        }
    }

    @Override
    public SongStatistic getSongStatisticByNameAndDate(String songName, Date orderDate) {
        try {
            return (SongStatistic) entityManager.createQuery("FROM SongStatistic where songName = :songName and " +
                    "orderDate = :orderDate")
                    .setParameter("songName", songName)
                    .setParameter("orderDate", orderDate)
                    .getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

}
