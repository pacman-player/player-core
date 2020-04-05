package spring.app.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import spring.app.service.abstraction.SongService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class PlayerPaths {

    private final Logger LOGGER = LoggerFactory.getLogger(PlayerPaths.class);
    private final String separator = File.separator;
    private SongService songService;
    private Path defaultMusicPath;

    public PlayerPaths(@Value("${music.path}") String musicPath, SongService songService) {
        this.defaultMusicPath = Paths.get(musicPath).toAbsolutePath();
        this.songService = songService;
    }

    public Path getSongDir(Long songId) {
        Long authorId = songService.getAuthorIdBySongId(songId);
        Path authorPath = defaultMusicPath.resolve(String.valueOf(authorId));
        String filename = songId + ".mp3";
        try {
            if (!Files.exists(authorPath)) {
                LOGGER.trace("Creating authorPath: " + authorPath.toAbsolutePath().toString());
                Files.createDirectories(authorPath);
            }
            return Paths.get(String.format("%s%s%s", authorPath, separator, filename));
        } catch (IOException ex) {
            LOGGER.error("Error creating directory: " + authorPath.toAbsolutePath().toString());
            LOGGER.error(ex.getMessage(), ex);
        }
        return defaultMusicPath.resolve(filename);
    }
}
