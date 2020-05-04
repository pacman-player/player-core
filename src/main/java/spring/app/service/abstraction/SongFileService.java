package spring.app.service.abstraction;

import org.springframework.stereotype.Service;
import spring.app.model.Author;
import spring.app.model.Song;


public interface SongFileService {

    public void deleteSongFile(Song song);

    public void deleteSongFile(Author author);

}

