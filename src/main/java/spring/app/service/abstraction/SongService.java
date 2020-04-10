package spring.app.service.abstraction;

import spring.app.model.Song;

import java.sql.Timestamp;
import java.util.List;

public interface SongService {

    void addSong(Song song);

    void updateSong(Song song);

    void deleteSongById(Long id);

    boolean isExist(String name);

    Song getSongById(Long id);

    Song getByName(String name);

    Song getByAuthorAndName(String author, String name);

    /**
     * Возвращает песню по id
     *
     * @return Song
     */
    Song getById(long songId);

    /**
     * возвращает все песни, в которых содержиться передаваемое значение
     *
     * @return List<Song>
     */
    List<Song> findSongsByNameContaining(String name);

    List<Song> getAllSongs();

    List<Song> getByCreatedDateRange(Timestamp dateFrom, Timestamp dateTo);

    List<Song> getAllSongInSongCompilation(Long id);

    List<Song> getAllApprovedSongs();

    List<Song> getApprovedSongsPage(int pageNumber, int pageSize);

    int getLastApprovedSongsPageNumber(int pageSize);

    Long getSongIdByAuthorAndName(String author, String name);

    Long getAuthorIdBySongId(Long songId);
}
