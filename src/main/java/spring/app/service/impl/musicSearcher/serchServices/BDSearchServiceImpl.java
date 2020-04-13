package spring.app.service.impl.musicSearcher.serchServices;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.app.model.Song;
import spring.app.service.abstraction.DownloadMusicService;
import spring.app.service.abstraction.SongService;
import spring.app.service.entity.Track;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service("bdSearchServiceImpl")
@Transactional
public class BDSearchServiceImpl implements DownloadMusicService {

    private final static Logger LOGGER = LoggerFactory.getLogger(BDSearchServiceImpl.class);

    private final SongService songService;

    public BDSearchServiceImpl(SongService songService) {
        this.songService = songService;
    }

    /**
     * Основной метод получения искомого трека.
     *
     * @param author - имя исполнителя
     * @param song   - название песни
     * @return возвращение нового экземпляра класса Track.
     */
    @Override
    public Track getSong(String author, String song) throws IOException {
        Song tmpSong = songService.getBySearchRequests(author, song);
        LOGGER.debug("Скачивание трека: {} - {} c локального хранилища", author, song);
        if (tmpSong == null) {
            LOGGER.debug("Скачивание трека: {} - {} c c локального хранилища неуспешно! :(", author, song);
            return null;
        }
        String authorName = tmpSong.getAuthor().getName();
        String songName = tmpSong.getName();
        String fullTrackName = authorName + "-" + songName;
        long authorId = tmpSong.getAuthor().getId();
        long songId = tmpSong.getId();
        byte[] musicByteArray = Files.readAllBytes(Paths.get(String.format("music/%d/%d.mp3", authorId, songId)));
        return new Track(authorName, songName, fullTrackName, musicByteArray);
    }
}
