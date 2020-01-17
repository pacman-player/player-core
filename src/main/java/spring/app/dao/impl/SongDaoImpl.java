package spring.app.dao.impl;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import spring.app.dao.abstraction.SongDao;
import spring.app.model.Song;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class SongDaoImpl extends AbstractDao<Long, Song> implements SongDao {
    SongDaoImpl() {
        super(Song.class);
    }

    @Override
    public Song getByName(String name) {
        TypedQuery<Song> query = entityManager.createQuery("FROM Song WHERE name = :name", Song.class);
        query.setParameter("name", name);
        Song song;
        try {
            song = query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
        return song;
    }

    //закоментровал вариант с JOIN FETCH, который не получился
//    @Override
//    @Fetch(FetchMode.JOIN)
//    public List<Song> getAll() {
//        TypedQuery<Song> query = entityManager.createQuery("SELECT s FROM Song s " +
//                "JOIN FETCH s.author " +
//                "JOIN FETCH s.genre " +
//                "JOIN FETCH s.songCompilations " +
//                "JOIN FETCH s.songQueues"
//                , Song.class);
//        List<Song> allSong;
//        try {
//            allSong = query.getResultList();
//        } catch (NoResultException e) {
//            return null;
//        }
//        return allSong;
//    }
}
