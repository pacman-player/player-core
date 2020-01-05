package spring.app.service.abstraction;

import spring.app.model.Song;

public interface SongService {
    Song getByName(String name);
    Song getSongById(long id);
    void addSong(Song song);
    boolean isExist(String name);
}
