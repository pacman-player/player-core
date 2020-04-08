package spring.app.service.abstraction;

import spring.app.model.Song;

import java.sql.Timestamp;
import java.util.List;


public interface SongService {

    Song getByName(String name);

    Song getByAuthorAndName(String author, String name);

    Song getSongById(Long id);

    void addSong(Song song);

    void updateSong(Song song);

    void deleteSongById(Long id);

    boolean isExist(String name);

    List<Song> getByCreatedDateRange(Timestamp dateFrom, Timestamp dateTo);

    List<Song> getAllSongInSongCompilation(Long id);

    List<Song> getAllSongs();

    List<Song> getAllApprovedSongs();

    List<Song> getApprovedSongsPage(int pageNumber, int pageSize);

    int getLastApprovedSongsPageNumber(int pageSize);

    /**
     * возвращает все песни, в которых содержиться передаваемое значение
     *
     * @return List<Song>
     */
    List<Song> findSongsByNameContaining(String name);

    /**
     * Возвращает песню по id
     *
     * @return Song
     */
    Song getById(long songId);

    Long getSongIdByAuthorAndName(String author, String name);

    Long getAuthorIdBySongId(Long songId);
}
