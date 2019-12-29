package spring.app.dao.impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import spring.app.dao.abstraction.SongDao;
import spring.app.model.Song;
import spring.app.model.SongDownloadRequestInfo;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
@Transactional
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

    @Override
    public Song getBySongNameAndAuthorName(Long idAuthor, String songName) {
        TypedQuery<Song> query = entityManager.createQuery("FROM Song WHERE name = :name and author_id=:author_id", Song.class);
        query.setParameter("name", songName);
        query.setParameter("author_id", idAuthor);
        Song song;
        try {
            song = query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
        return song;
    }

    @Override
    public List<Song> getByAuthor(Long idAuthor) {
        List<Song> resultList = null;
        try {
            TypedQuery<Song> query = entityManager.createQuery("SELECT s FROM Song s WHERE s.author_id = :author_id", Song.class);
            query.setParameter("author_id", idAuthor);
            resultList= query.getResultList();
        } catch (NoResultException e) {
            //logger
        }

        return resultList;
    }
}
