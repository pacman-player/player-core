package spring.app.service.abstraction;

import spring.app.model.Song;

import java.sql.Timestamp;
import java.util.List;

public interface SongService extends GenericService<Long, Song>{

    boolean isExist(String name);

    Song getByName(String name);

    //TODO: кандидат на удаление, не используется
//    Song getByAuthorAndName(String author, String name);

    Song getBySearchRequests(String author, String name);

    List<Song> getByCreatedDateRange(Timestamp dateFrom, Timestamp dateTo);

    List<Song> getAllSongInSongCompilation(Long id);

    List<Song> getAllApprovedSongs();

    List<Song> getApprovedSongsPage(int pageNumber, int pageSize);

    /**
     * Возвращает песню по id жанра
     *
     * @return List<Song>
     */
    List<Song> findSongsByGenreId(Long id);

    int getLastApprovedSongsPageNumber(int pageSize);

    Long getSongIdByAuthorAndName(String author, String name);

    Long getAuthorIdBySongId(Long songId);
}
