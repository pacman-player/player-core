package spring.app.dao.abstraction;

import spring.app.model.Song;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface SongDao extends GenericDao<Long, Song> {

    Song getByName(String name);

    Song getByAuthorAndName(String author, String name);

    List<Song> getAllWithGenreByGenreId(Long id);

    List<Song> getByCreatedDateRange(Timestamp from, Timestamp to);

    boolean isExist(String name);

    void bulkRemoveSongsByAuthorId(Long id);
}

