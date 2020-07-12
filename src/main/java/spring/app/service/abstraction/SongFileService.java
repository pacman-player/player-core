package spring.app.service.abstraction;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import spring.app.model.Author;
import spring.app.model.Song;

import java.io.IOException;


public interface SongFileService {

    void deleteSongFile(Song song);

    void deleteSongFile(Author author);

    void saveSongFile(Song song, MultipartFile file) throws IOException, IllegalArgumentException;
}

