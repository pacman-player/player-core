package spring.app.service.impl.musicSearcher;


import com.google.common.util.concurrent.SimpleTimeLimiter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.app.configuration.DownloadMusicServiceFactory;
import spring.app.service.abstraction.DataUpdateService;
import spring.app.service.abstraction.DownloadMusicService;
import spring.app.service.abstraction.GenreDefinerService;
import spring.app.service.abstraction.MusicSearchService;
import spring.app.service.entity.Track;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static java.util.concurrent.Executors.newCachedThreadPool;

/**
 * Класс для поиска песен по разным сервисам, в зависимости от результата
 */
@Service
@Transactional
public class MusicSearchServiceImpl implements MusicSearchService {
    private final static Logger LOGGER = LoggerFactory.getLogger(MusicSearchServiceImpl.class);
    private Track track;

    @Autowired
    private GenreDefinerService genreDefiner;

    @Autowired
    private DataUpdateService dataUpdater;

    @Autowired
    private DownloadMusicServiceFactory downloadMusicServiceFactory;


    public MusicSearchServiceImpl(DataUpdateService dataUpdater) throws IOException {
        this.dataUpdater = dataUpdater;
    }


    /**
     * Метод который ищет трек в списке добавленных сервисов поиска музыки {@link DownloadMusicService}
     *
     * @param author имя исполнителя
     * @param song   название песни
     * @return экземпляр {@link Track}
     * @throws IOException
     */
    @Override
    public Track getSong(String author, String song) throws IOException {
        // складываем сервисы поиска в лист
        // проходим в цикле по каждому сервису,
        // пытаемся найти песню и при положительном исходе брейкаем цикл
        for (DownloadMusicService service : downloadMusicServiceFactory.getDownloadServices()) {
            try {
                //noinspection UnstableApiUsage
                track = SimpleTimeLimiter
                        .create(newCachedThreadPool())
                        .callWithTimeout(
                                () -> service.getSong(author, song),
                                120,
                                TimeUnit.SECONDS);
            } catch (TimeoutException | InterruptedException | ExecutionException e) {
                LOGGER.error(">>>>> Searching with {} service exceeded given timeout! :(",
                        service.getClass().getSimpleName().replaceAll("\\$.+", ""));
            }
            if (track != null) {
                break;
            }
        }
        return track;
    }

    //определяет жанр песни
    public String[] getGenre(String trackName) throws IOException {
        return genreDefiner.defineGenre(trackName);
    }

    //заносит данные скачаной песни в бд и возвращает id песни
    public Long updateData(Track track) throws IOException {
        String[] genreNames = getGenre(track.getFullTrackName());
        return dataUpdater.updateData(track.getAuthor(), track.getSong(), genreNames);
    }

    //заносит данные песни из папки при инициализации в бд и возвращает id песни
    public Long updateData(String fullTrackName, String author, String songName) throws IOException {
        String[] genreNames = getGenre(fullTrackName);
        return dataUpdater.updateData(author, songName, genreNames);
    }
}
