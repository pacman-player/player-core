package spring.app.dao.impl.dto;

import org.hibernate.SQLQuery;
import org.hibernate.transform.ResultTransformer;
import org.springframework.stereotype.Repository;
import spring.app.dao.abstraction.dto.SongDtoDao;
import spring.app.dto.BotSongDto;
import spring.app.dto.SongDto;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigInteger;
import java.util.List;

@Repository
public class SongDtoDaoImpl implements SongDtoDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<SongDto> getAll() {
        List<SongDto> songDtos = entityManager.createQuery(
                "SELECT new spring.app.dto.SongDto(s.id, s.name, s.isApproved, s.author.name, s.genre.name) FROM Song s",
                SongDto.class
        )
                .getResultList();

        return songDtos;
    }

    @Override
    public List<SongDto> listOfSongsByTag(String tag) {
        List<SongDto> songDtos =  entityManager.createQuery(
                "SELECT new spring.app.dto.SongDto(s.id, s.name, s.isApproved, s.author.name, s.genre.name) " +
                        "FROM Song s INNER JOIN s.tags t WHERE t.name = :name", SongDto.class)
                .setParameter("name", tag)
                .getResultList();
        return songDtos;
    }

    @Override
    public SongDto getById(long songId) {
        return entityManager.createQuery("SELECT new spring.app.dto.SongDto(s.id, s.name, s.isApproved, s.author.name, s.genre.name) " +
                "FROM Song s where s.id = :id", SongDto.class)
                .setParameter("id", songId)
                .setMaxResults(1)
                .getResultList()
                .get(0);
    }

    @Override
    public List<BotSongDto> getBySearchRequests(String author, String name) {
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

        String ftsQuery = "SELECT s.id id, s.name sname, a.name aname FROM songs s INNER JOIN tag_on_song ts ON s.id = ts.song_id " +
                                                "INNER JOIN tags t ON ts.tag_id = t.id " +
                                                "INNER JOIN authors a ON s.author_id = a.id " +
                                                "WHERE to_tsvector(:searchRequest) @@ plainto_tsquery(t.name) " +
                                                "GROUP BY s.id, a.id ORDER BY count(*) DESC LIMIT 3";
        return entityManager.createNativeQuery(ftsQuery)
                                        .setParameter("searchRequest", author + ' ' + name)
                                        .unwrap(SQLQuery.class)
                                        .setResultTransformer(new BotSongDtoTransformer())
                                        .list();
    }

    private class BotSongDtoTransformer implements ResultTransformer {

        @Override
        public Object transformTuple(Object[] tuple, String[] aliases) {
            long songId = ((BigInteger) tuple[0]).longValue();
            String songName = (String) tuple[1];
            String authorName = (String) tuple[2];
            return new BotSongDto(songId, songName + " - " + authorName);
        }

        @Override
        public List transformList(List list) {
            return list;
        }
    }

    @Override
    public List<SongDto> getAllWithGenreByGenreIdDto(Long id) {

        List<SongDto> list = entityManager.createQuery("SELECT new spring.app.dto.SongDto(s.id, s.name, s.isApproved, s.author.name, " +
                "s.genre.name) FROM Song s JOIN s.genre g WHERE g.genre_id = :id", SongDto.class)
                .setParameter("id", id)
                .getResultList();
        return list;
    }

    @Override
    public List<SongDto> getAllApprovedDto() {
        List<SongDto> list = entityManager.createQuery("SELECT new spring.app.dto.SongDto(s.id, s.name, s.isApproved, s.author.name, " +
                "s.genre.name) FROM Song s  WHERE s.isApproved = true", SongDto.class)
                .getResultList();
        return list;

//        String genericClassName = Song.class.toGenericString();
//        genericClassName = genericClassName.substring(genericClassName.lastIndexOf('.') + 1);
//        String hql = "FROM " + genericClassName + " as c WHERE c.isApproved = true";
//        TypedQuery<SongDto> query = entityManager.createQuery(hql, SongDto.class);
//        return query.getResultList();

    }


}
