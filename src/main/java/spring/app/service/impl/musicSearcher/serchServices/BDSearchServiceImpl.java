package spring.app.service.impl.musicSearcher.serchServices;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.app.model.Song;
import spring.app.service.abstraction.DownloadMusicService;
import spring.app.service.abstraction.MusicService;
import spring.app.service.abstraction.SongService;
import spring.app.service.entity.Track;

import java.io.IOException;

@Service("bdSearchServiceImpl")
@Transactional
public class BDSearchServiceImpl implements DownloadMusicService {

    private final static Logger LOGGER = LoggerFactory.getLogger(BDSearchServiceImpl.class);

    private final SongService songService;
    private final MusicService musicService;

    public BDSearchServiceImpl(SongService songService, MusicService musicService) {
        this.songService = songService;
        this.musicService = musicService;
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
        String authorName = tmpSong.getAuthor().getName();
        String songName = tmpSong.getName();
        String fullTrackName = authorName + "-" + songName;

        byte[] musicByteArray = musicService.getMusicByteArray(authorName, songName);

        return new Track(authorName, songName, fullTrackName, musicByteArray);
    }
}
