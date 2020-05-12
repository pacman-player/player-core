package spring.app.service.impl.musicSearcher;


import com.google.common.util.concurrent.SimpleTimeLimiter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.app.configuration.DownloadMusicServiceFactory;
import spring.app.dao.abstraction.CounterDao;
import spring.app.model.CounterType;
import spring.app.service.abstraction.DataUpdateService;
import spring.app.service.abstraction.DownloadMusicService;
import spring.app.service.abstraction.GenreDefinerService;
import spring.app.service.abstraction.MusicSearchService;
import spring.app.service.entity.Track;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;
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

    private final GenreDefinerService genreDefiner;
    private final DataUpdateService dataUpdater;
    private final DownloadMusicServiceFactory downloadMusicServiceFactory;
    private final CounterDao counterDao;

    @Autowired
    public MusicSearchServiceImpl(GenreDefinerService genreDefiner, DataUpdateService dataUpdater,
                                  DownloadMusicServiceFactory downloadMusicServiceFactory, CounterDao counterDao) {
        this.genreDefiner = genreDefiner;
        this.dataUpdater = dataUpdater;
        this.downloadMusicServiceFactory = downloadMusicServiceFactory;
        this.counterDao = counterDao;
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
        int serviceCount = counterDao.getValue(CounterType.DOWNLOAD_MUSIC_SERVICE);
        List<DownloadMusicService> downloadMusicServices = downloadMusicServiceFactory.getDownloadServices();
        for (int i = serviceCount; i < downloadMusicServices.size(); ) {
            try {
                //noinspection UnstableApiUsage
                int finalI = i;
                track = SimpleTimeLimiter
                        .create(newCachedThreadPool())
                        .callWithTimeout(
                                () -> downloadMusicServices.get(finalI).getSong(author, song),
                                120,
                                TimeUnit.SECONDS);
            } catch (TimeoutException | InterruptedException | ExecutionException e) {
                LOGGER.error(">>>>> Searching with {} service exceeded given timeout! :(",
                        downloadMusicServices.get(i).getClass().getSimpleName().replaceAll("\\$.+", ""));
            }
            if (track != null) {
                if (i == downloadMusicServices.size() - 1) {
                    counterDao.restart(CounterType.DOWNLOAD_MUSIC_SERVICE);
                } else {
                    counterDao.setTo(CounterType.DOWNLOAD_MUSIC_SERVICE, i);
                }
                break;
            }
            i++;
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
