package spring.app.dao.impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import spring.app.dao.abstraction.SongDao;
import spring.app.model.Song;

import javax.persistence.NoResultException;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class SongDaoImpl extends AbstractDao<Long, Song> implements SongDao {

    SongDaoImpl() {
        super(Song.class);
    }

    @Override
    public Song getByName(String name) {
        try {
            return entityManager.createQuery("SELECT u FROM Song u WHERE u.name = :name", Song.class)
                    .setParameter("name", name)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public List<Song> findByNameContaining(String param) {
        try {
            return entityManager.createQuery("SELECT s from Song s WHERE s.name LIKE :part",
                    Song.class)
                    .setParameter("part", "%" + param + "%")
                    .getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }
}
