package spring.app.service.impl.musicSearcher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.app.service.abstraction.DownloadMusicService;
import spring.app.service.abstraction.MusicSearchService;
import spring.app.service.impl.musicSearcher.serchService.DownloadMusicVkRuServiceImpl;
import spring.app.service.impl.musicSearcher.serchService.KrolikSaitServiceImpl;
import spring.app.service.impl.musicSearcher.serchService.MuzofondfmMusicSearchImpl;
import spring.app.service.impl.musicSearcher.serchService.ZaycevSaitServiceImpl;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * класс для поиска песен по разным сервисам, в зависимости от результата
 */
@Service
@Transactional
public class MusicSearchServiceImpl implements MusicSearchService {


    private DownloadMusicVkRuServiceImpl vkMusic;
    private KrolikSaitServiceImpl krolikMusic;
    private ZaycevSaitServiceImpl zaycevMusic;
    private MuzofondfmMusicSearchImpl muzofondMusic;

    private byte[] track;
    private String trackName;

    public MusicSearchServiceImpl(DownloadMusicVkRuServiceImpl vkMusic,
                                  ZaycevSaitServiceImpl zaycevMusic,
                                  MuzofondfmMusicSearchImpl muzofondMusic,
                                  KrolikSaitServiceImpl krolikMusic) {
        this.vkMusic = vkMusic;
        this.zaycevMusic = zaycevMusic;
        this.muzofondMusic = muzofondMusic;
        this.krolikMusic = krolikMusic;
    }

    //при появлении новых сервисов с методом getSong(author,song) просто добавить в коллекцию

    List<DownloadMusicService> musicSearchServices = Arrays.asList(zaycevMusic, vkMusic, muzofondMusic, krolikMusic);

    @Override
    public byte[] getSong(String author, String song) throws IOException {

        for (Iterator<DownloadMusicService> it = musicSearchServices.iterator(); it.hasNext(); ) {
            DownloadMusicService service = it.next();
            track = service.getSong(author, song);
            trackName = service.getTrackName();
            if (track != null) break;
        }
        return track;
    }

    public String getTrackName() {
        return trackName;
    }


}
