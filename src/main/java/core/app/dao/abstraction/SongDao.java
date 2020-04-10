package core.app.dao.abstraction;

import core.app.model.Song;

import java.sql.Timestamp;
import java.util.List;

public interface SongDao extends GenericDao<Long, Song> {

    Song getByName(String name);

    Song getByAuthorAndName(String author, String name);

    List<Song> getAllWithGenreByGenreId(Long id);

    List<Song> getByCreatedDateRange(Timestamp from, Timestamp to);

    List<Song> getAllApproved();

    List<Song> getApprovedPage(int pageNumber, int pageSize);

    int getLastApprovedPageNumber(int pageSize);

    void bulkRemoveSongsByAuthorId(Long id);

    boolean isExist(String name);

    Long getSongIdByAuthorAndName(String author, String name);

    Long getAuthorIdBySongId(Long songId);
}

