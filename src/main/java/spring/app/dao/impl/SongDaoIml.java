package spring.app.dao.impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import spring.app.dao.abstraction.SongDao;
import spring.app.model.Song;

import javax.persistence.TypedQuery;

@Repository
@Transactional
public class SongDaoIml extends AbstractDao<Long, Song> implements SongDao {
    SongDaoIml() {
        super(Song.class);
    }

    @Override
    public Song getByName(String name) {
        TypedQuery<Song> query = entityManager.createQuery("FROM Song WHERE name = :name", Song.class);
        query.setParameter("name", name);
        return query.getSingleResult();
    }
}
