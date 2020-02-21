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
import java.util.Comparator;
import java.util.List;
import java.util.Set;

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
        return entityManager.createQuery("SELECT sq FROM SongQueue sq WHERE sq.company.id = :id", SongQueue.class).setParameter("id", id).getResultList();
    }
}
