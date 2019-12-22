package spring.app.service.abstraction;

import spring.app.model.Author;
import spring.app.model.Song;

import java.util.HashSet;
import java.util.List;

public interface SongService {
    Song getByName(String name);
    void addSong(Song song);
    boolean isExist(String name);

    /**
     * возвращает все песни, в которых содержиться передаваемое значение
     * @return List<Author>
     */
    List<Song> findSongsByNameContaining(String name);
}
