package spring.app.service.impl;

import com.google.common.io.Files;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import spring.app.model.Author;
import spring.app.model.Song;
import spring.app.service.abstraction.SongFileService;

import java.io.File;
import java.io.IOException;

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

    @Override
    public void saveSongFile(Song song, MultipartFile file) throws IOException, IllegalArgumentException {
        if (!isSongFile(file)) {
            throw new IllegalArgumentException();
        }

        File directory = new File(musicPath + song.getAuthor().getId());

        if (!directory.exists()) {
            boolean directoryCreated = directory.mkdir();
            if (!directoryCreated) {
                throw new IOException("Произошла ошибка при создании директории");
            }
        }

        File songFile = new File(directory.getAbsolutePath(), song.getId() + ".mp3");
        file.transferTo(songFile);
    }

    private boolean isSongFile(MultipartFile file) {
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        return extension.equals("mp3");
    }
}
