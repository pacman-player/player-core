package spring.app.service.abstraction;

import spring.app.model.Song;

import java.util.List;

public interface SongService {
    List<Song> getAllSong();
    Song getByName(String name);
    void addSong(Song song);
    void deleteSongById(Long id);
    boolean isExist(String name);
}
