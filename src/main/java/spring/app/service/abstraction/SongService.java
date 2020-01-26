package spring.app.service.abstraction;

import spring.app.model.Song;

import java.util.List;

public interface SongService {
    List<Song> getAllSong();
    List<Song> getAllSongFetchModeJoin();
    Song getByName(String name);
    Song getSongById(Long id);
    void addSong(Song song);
    void updateSong(Song song);
//    void updateSongNonLazy(Song song);
    void deleteSongById(Long id);
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
