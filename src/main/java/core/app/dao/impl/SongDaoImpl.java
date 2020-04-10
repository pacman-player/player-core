package core.app.dao.impl;

import core.app.model.Song;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import core.app.dao.abstraction.SongDao;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.sql.Timestamp;
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

    @Override
    public List<Song> getAllWithGenreByGenreId(Long id) {
        TypedQuery<Song> query = entityManager.createQuery("FROM Song WHERE genre_id = :id", Song.class);
        query.setParameter("id", id);

        List<Song> songs;
        try {
            songs = query.getResultList();
        } catch (NoResultException e) {
            return null;
        }
        return songs;
    }

    @Override
    public Song getByAuthorAndName(String author, String name) {
        TypedQuery<Song> query = entityManager.createQuery("SELECT s FROM Song s WHERE s.author.name = :author AND s.name = :name", Song.class);
        query.setParameter("author", author).setParameter("name", name);
        Song song;
        try {
            song = query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
        return song;
    }

    @Override
    public List<Song> findByNameContaining(String param) {
        try {
            String hql = "FROM Song s WHERE s.name LIKE :param";
            TypedQuery<Song> query = entityManager.createQuery(hql, Song.class);
            // знак % обозначает, что перед передаваемым значение может быть, или колько угодно символов, или ноль.
            query.setParameter("param", "%" + param + "%");

            return query.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    public boolean isExist(String name) {
        TypedQuery<Song> query = entityManager.createQuery(
                "SELECT s FROM Song s WHERE s.name = :name", Song.class);
        query.setParameter("name", name);
        List<Song> list = query.getResultList();
        return !list.isEmpty();
    }

    @Override
    public List<Song> getByCreatedDateRange(Timestamp dateFrom, Timestamp dateTo) {
        return entityManager
                .createQuery("FROM Song s WHERE s.createdAt >= :dateFrom AND s.createdAt <= :dateTo ORDER BY s.createdAt", Song.class)
                .setParameter("dateFrom", dateFrom)
                .setParameter("dateTo", dateTo).getResultList();
    }

    @Override
    public void bulkRemoveSongsByAuthorId(Long authorId) {
        entityManager.createQuery("DELETE FROM Song s WHERE s.author.id = :authorId")
                .setParameter("authorId", authorId)
                .executeUpdate();
        entityManager.flush();
    }
}
