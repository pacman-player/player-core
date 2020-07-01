package spring.app.dao.impl;

import org.hibernate.Hibernate;
import org.springframework.stereotype.Repository;
import spring.app.dao.abstraction.SongQueueDao;
import spring.app.model.Company;
import spring.app.model.Song;
import spring.app.model.SongQueue;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

@Repository
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
        initLazyFields(songQueue);
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

    @Override
    public void deleteAllSongQueues(Set<SongQueue> songQueues) {
        songQueues.forEach(entityManager::remove);
    }

    @Override
    public void deletePlayedSong(Set<SongQueue> songQueues) {
        entityManager.remove(songQueues.stream().min(Comparator.comparing(SongQueue::getPosition)).get());
    }

    @Override
    public List<SongQueue> getByCompanyId(Long id) {
        List<SongQueue> list = entityManager.createQuery("SELECT sq FROM SongQueue sq WHERE sq.company.id = :id", SongQueue.class).setParameter("id", id).getResultList();
        for (SongQueue sq :
                list) {
            initLazyFields(sq);
        }
        return list;
    }

    private void initLazyFields(SongQueue songQueue) {
        Hibernate.initialize(songQueue.getSong());
        Hibernate.initialize(songQueue.getCompany());
    }
}
