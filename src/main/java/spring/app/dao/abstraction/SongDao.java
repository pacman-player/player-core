package spring.app.dao.abstraction;

import spring.app.model.Song;

import java.sql.Timestamp;
import java.util.List;

public interface SongDao extends GenericDao<Long, Song> {

    void bulkRemoveSongsByAuthorId(Long id);

    boolean isExist(String name);

    Song getByName(String name);

    //TODO: кандидат на удаление, не используется
//    Song getByAuthorAndName(String author, String name);

    Song getBySearchRequests(String author, String name);

    List<Song> getAllWithGenreByGenreId(Long id);

    List<Song> getByCreatedDateRange(Timestamp from, Timestamp to);

    List<Song> getAllApproved();

    List<Song> getApprovedPage(int pageNumber, int pageSize);

    int getLastApprovedPageNumber(int pageSize);

    Long getSongIdByAuthorAndName(String author, String name);

    Long getAuthorIdBySongId(Long songId);
}

