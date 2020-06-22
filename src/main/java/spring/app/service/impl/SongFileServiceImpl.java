package spring.app.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import spring.app.model.Author;
import spring.app.model.Song;
import spring.app.service.abstraction.SongFileService;

import java.io.File;

@Service
public class SongFileServiceImpl implements SongFileService {

    @Value("${music.path}")
    private String musicPath;

    public void deleteSongFile(Song song){
        String pathSong = musicPath + song.getAuthor().getId() + File.separatorChar + song.getId() + ".mp3";
        File fileSong = new File(pathSong);
        fileSong.delete();
    }

    public void deleteSongFile(Author author){
        File fileDirectory = new File(musicPath + author.getId());
        File[] contents = fileDirectory.listFiles();
        if (contents != null) {
            for (File f : contents) {
                f.delete();
            }
        }
        fileDirectory.delete();

    }
}
