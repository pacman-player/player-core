package spring.app.service.impl.musicSearcher.searchServices;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.app.model.Song;
import spring.app.service.abstraction.LoadSongService;
import spring.app.service.abstraction.SongService;
import spring.app.service.entity.Track;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
public class LoadSongServiceImpl implements LoadSongService {

    private final static Logger LOGGER = LoggerFactory.getLogger(LoadSongServiceImpl.class);

    private final SongService songService;

    public LoadSongServiceImpl(SongService songService) {
        this.songService = songService;
    }

    /**
     * Скачивание трека из базы данных
     *
     * @param songId - Id песни в БД
     * @return Найденный трек
     */
    @Override
    public Track getSong(Long songId) throws IOException {
        LOGGER.debug("Скачивание трека c локального хранилища. Song id = {}", songId);
        Song tmpSong = songService.getById(songId);
        if (tmpSong == null) {
            LOGGER.debug("Скачивание трека c локального хранилища неуспешно! Song id = {}", songId);
            return null;
        }
        String authorName = tmpSong.getAuthor().getName();
        String songName = tmpSong.getName();
        String fullTrackName = authorName + "-" + songName;
        long authorId = tmpSong.getAuthor().getId();
        byte[] musicByteArray = Files.readAllBytes(Paths.get(String.format("music/%d/%d.mp3", authorId, songId)));
        return new Track(authorName, songName, fullTrackName, musicByteArray);
    }
}
