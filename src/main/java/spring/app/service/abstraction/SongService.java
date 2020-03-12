package spring.app.service.abstraction;

import spring.app.model.Song;

import java.util.List;


public interface SongService {

    List<Song> getAllSong();

    Song getByName(String name);

    Song getByNameAndAuthor(String name, String author);

    Song getSongById(Long id);

    void addSong(Song song);

    void updateSong(Song song);

    void deleteSongById(Long id);

    boolean isExist(String name);

    List<Song> getAllSongInSongCompilation(Long id);

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
}
