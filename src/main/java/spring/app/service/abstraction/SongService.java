package spring.app.service.abstraction;

import spring.app.model.Author;
import spring.app.model.Song;

import java.util.List;
import java.util.HashSet;

public interface SongService {

    Song getByName(String name);

    void addSong(Song song);

    boolean isExist(String name);

    /**
     * возвращает все песни, в которых содержиться передаваемое значение
     * @return List<Author>
     */
    List<Song> findSongsByNameContaining(String name);

    /**
     * Возвращает песню по id
     * @return Song
     */
    Song getById(long songId);

    List<Song> getAllSongInSongCompilation(Long id);

//    List<Song> getAllSong();
//
//    void deleteSongById(Long id);
//
//    Song getSongById(long id);
//
//    void updateSong(Song song);
}
