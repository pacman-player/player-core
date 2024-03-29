package spring.app.dao.impl;

import org.springframework.stereotype.Repository;
import spring.app.dao.abstraction.SongDao;
import spring.app.model.Song;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class SongDaoImpl extends AbstractDao<Long, Song> implements SongDao {

    SongDaoImpl() {
        super(Song.class);
    }

    @Override
    public void bulkRemoveSongsByAuthorId(Long authorId) {
        entityManager.createQuery("DELETE FROM Song s WHERE s.author.id = :authorId")
                .setParameter("authorId", authorId)
                .executeUpdate();
        entityManager.flush();
    }

    @Override
    public boolean isExist(String name) {
        TypedQuery<Song> query = entityManager.createQuery(
                "SELECT s FROM Song s WHERE s.name = :name", Song.class);
        query.setParameter("name", name);
        List<Song> list = query.getResultList();
        return !list.isEmpty();
    }

    @Override
    public Song getByName(String name) {
        TypedQuery<Song> query = entityManager.createQuery("select s FROM Song s WHERE s.name = :name", Song.class);
        query.setParameter("name", name);
        Song song;
        try {
            song = query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
        return song;
    }

    //TODO: кандидат на удаление, не используется
//    @Override
//    public Song getByAuthorAndName(String author, String name) {
//        TypedQuery<Song> query = entityManager.createQuery("SELECT s FROM Song s WHERE s.author.name = :author AND s.name = :name", Song.class);
//        query.setParameter("author", author).setParameter("name", name);
//        Song song;
//        try {
//            song = query.getSingleResult();
//        } catch (NoResultException e) {
//            return null;
//        }
//        return song;
//    }

    //TODO: кандидат на удаление, не используется, аналогичный метод есть в SongDtoDaoImpl
//    @Override
//    public List<Song> getAllWithGenreByGenreId(Long id) {
//        TypedQuery<Song> query = entityManager.createQuery("select s FROM Song s WHERE s.genre_id = :id", Song.class);
//        query.setParameter("id", id);
//
//        List<Song> songs;
//        try {
//            songs = query.getResultList();
//        } catch (NoResultException e) {
//            return null;
//        }
//        return songs;
//    }

    @Override
    public List<Song> getByCreatedDateRange(Timestamp dateFrom, Timestamp dateTo) {
        return entityManager
                .createQuery("select s from Song s WHERE s.createdAt >= :dateFrom AND s.createdAt <= :dateTo ORDER BY s.createdAt", Song.class)
                .setParameter("dateFrom", dateFrom)
                .setParameter("dateTo", dateTo).getResultList();
    }

    @Override
    public List<Song> getAllApproved() {
        String genericClassName = Song.class.toGenericString();
        genericClassName = genericClassName.substring(genericClassName.lastIndexOf('.') + 1);
        String hql = "FROM " + genericClassName + " as c WHERE c.isApproved = true";
        TypedQuery<Song> query = entityManager.createQuery(hql, Song.class);
        return query.getResultList();
    }

    @Override
    public List<Song> getApprovedPage(int pageNumber, int pageSize) {
        String genericClassName = Song.class.toGenericString();
        genericClassName = genericClassName.substring(genericClassName.lastIndexOf('.') + 1);
        String jql = "FROM " + genericClassName + " as c WHERE c.isApproved = true";
        TypedQuery<Song> query = entityManager.createQuery(jql, Song.class);
        query.setFirstResult((pageNumber - 1) * pageSize);
        query.setMaxResults(pageSize);
        return query.getResultList();
    }

    @Override
    public int getLastApprovedPageNumber(int pageSize) {
        String genericClassName = Song.class.toGenericString();
        genericClassName = genericClassName.substring(genericClassName.lastIndexOf('.') + 1);
        String jql = "Select count(*) from " + genericClassName + " WHERE isApproved = true";
        Query query = entityManager.createQuery(jql);
        long count = (long) query.getSingleResult();
        int lastPageNumber = (int) ((count / pageSize) + 1);
        return lastPageNumber;
    }

    @Override
    public Long getSongIdByAuthorAndName(String author, String name) {
        return entityManager.createQuery("SELECT s.id FROM Song s WHERE s.author.name = :author AND s.name = :name", Long.class)
                .setParameter("author", author)
                .setParameter("name", name)
                .getSingleResult();
    }

    @Override
    public Long getAuthorIdBySongId(Long songId) {
        return entityManager.createQuery("SELECT s.author.id FROM Song s WHERE s.id = :id", Long.class)
                .setParameter("id", songId)
                .getSingleResult();
    }

    //TODO: кандидат на удаление, дубликат в AbstractDao
    @Override
    public List<Song> findByNameContaining(String param) {
        try {
            String hql = "select s from Song s WHERE s.name LIKE :param";
            TypedQuery<Song> query = entityManager.createQuery(hql, Song.class);
            // знак % обозначает, что перед передаваемым значение может быть, или колько угодно символов, или ноль.
            query.setParameter("param", "%" + param + "%");

            return query.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public int deleteTagForSongs(List<Long> songIds, Long tagId) {
        return entityManager.createNativeQuery("DELETE FROM tag_on_song WHERE tag_id = :tag_id AND song_id IN :song_ids")
                .setParameter("tag_id", tagId)
                .setParameter("song_ids", songIds)
                .executeUpdate();
    }


    @Override
    public List<Long> getBySearchRequests(String author, String name) {
        // Version 1
//        entityManager.createNativeQuery("CREATE EXTENSION pg_trgm");
//        entityManager.createNativeQuery("CREATE INDEX IF NOT EXISTS trgm_index_songs ON songs USING gist (songs.search_tags gist_trgm_ops)");
//        String ftsQuery = "SELECT * FROM songs WHERE songs.search_tags % '" + author + " " + name + "'";
//        Query query = entityManager.createNativeQuery(ftsQuery, Song.class);

        // Version 2
//        entityManager.createNativeQuery("CREATE INDEX IF NOT EXISTS fts_index_songs ON songs USING gist (to_tsvector(songs.search_tags))");
//        String ftsQuery = String.format("SELECT * FROM songs, plainto_tsquery('%s %s') AS q " +
//                "WHERE to_tsvector(songs.search_tags) @@ q " +
//                "ORDER BY ts_rank(to_tsvector(songs.search_tags), q) DESC", author, name);

        String ftsQuery = "SELECT s.id id FROM songs s INNER JOIN tag_on_song ts ON s.id = ts.song_id " +
                                                            "INNER JOIN tags t ON ts.tag_id = t.id " +
                                                            "INNER JOIN authors a ON s.author_id = a.id " +
                                                            "WHERE to_tsvector(:searchRequest) @@ plainto_tsquery(t.name) and s.approved = true " +
                                                            "GROUP BY s.id, a.id ORDER BY count(*) DESC LIMIT 3";
        List<? extends Number> list = entityManager.createNativeQuery(ftsQuery)
                                                    .setParameter("searchRequest", author + ' ' + name)
                                                    .getResultList();
        List<Long> res = list.stream().map(i -> i.longValue()).collect(Collectors.toList());

        return res;
    }


}
