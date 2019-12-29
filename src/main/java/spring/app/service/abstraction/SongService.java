package spring.app.service.abstraction;

import spring.app.model.Song;

import java.util.List;

public interface SongService {
    Song getByName(String name);
    Song getBySongNameAndAuthorId(Long idAuthor, String songName);
    List<Song> getByAuthor(Long idAuthor);
    void addSong(Song song);
    boolean isExist(String name);
}
