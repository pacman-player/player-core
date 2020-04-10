package core.app.dao.abstraction;

import core.app.model.Song;

import java.sql.Timestamp;
import java.util.List;

public interface SongDao extends GenericDao<Long, Song> {

    Song getByName(String name);

    Song getByAuthorAndName(String author, String name);

    List<Song> getAllWithGenreByGenreId(Long id);

    boolean isExist(String name);

    List<Song> getByCreatedDateRange(Timestamp from, Timestamp to);

    void bulkRemoveSongsByAuthorId(Long id);
}

