package spring.app.dao.impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import spring.app.dao.abstraction.SongQueueDao;
import spring.app.model.Company;
import spring.app.model.Song;
import spring.app.model.SongQueue;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class SongQueueDaoImpl extends AbstractDao<Long, SongQueue> implements SongQueueDao {
    @PersistenceContext
    EntityManager entityManager;

    SongQueueDaoImpl() {
        super(SongQueue.class);
    }

    @Override
    public SongQueue getSongQueueBySongAndCompany(Song song, Company company) {
        SongQueue songQueue;
        try {
            songQueue = entityManager
                    .createQuery("FROM SongQueue WHERE song = :song and company = :company", SongQueue.class)
                    .setParameter("song", song)
                    .setParameter("company", company)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
        return songQueue;
    }

    @Override
    public long getLastSongQueuesNumberFromCompany(Company company) {
        SongQueue songQueue;
        try {
            songQueue = entityManager.createQuery("FROM SongQueue WHERE position=(SELECT MAX(sq.position) FROM SongQueue AS sq)", SongQueue.class)
                    .getSingleResult();
        } catch (NoResultException e) {
            return 0L;
        }
        if (songQueue != null) {
            return songQueue.getPosition();
        }
        return 0L;
    }
}
