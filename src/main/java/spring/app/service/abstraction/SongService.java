package spring.app.service.abstraction;

import spring.app.model.Song;

import java.util.List;

public interface SongService {
    Song getByName(String name);
    Song getSongById(long id);
    void addSong(Song song);
    boolean isExist(String name);
    List<Song> getAllSongInSongCompilation(Long id);

//    List<Song> getAllSong();
//
//    void deleteSongById(Long id);
//
//    Song getSongById(long id);
//
//    void updateSong(Song song);
}
