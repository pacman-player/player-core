package spring.app.service.impl.musicSearcher;

import org.springframework.stereotype.Service;
import spring.app.service.abstraction.DownloadMusicService;
import spring.app.service.abstraction.MusicSearchService;
import spring.app.service.entity.Track;
import spring.app.service.impl.musicSearcher.serchServices.DownloadMusicVkRuServiceImpl;
import spring.app.service.impl.musicSearcher.serchServices.KrolikSaitServiceImpl;
import spring.app.service.impl.musicSearcher.serchServices.MuzofondfmMusicSearchImpl;
import spring.app.service.impl.musicSearcher.serchServices.ZaycevSaitServiceImpl;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * класс для поиска песен по разным сервисам, в зависимости от результата
 */
@Service
@Transactional
public class MusicSearchServiceImpl implements MusicSearchService {

    private Track track;

    private DownloadMusicVkRuServiceImpl vkMusic;
    private KrolikSaitServiceImpl krolikMusic;
    private ZaycevSaitServiceImpl zaycevMusic;
    private MuzofondfmMusicSearchImpl muzofondMusic;

    public MusicSearchServiceImpl(DownloadMusicVkRuServiceImpl vkMusic,
                                  ZaycevSaitServiceImpl zaycevMusic,
                                  MuzofondfmMusicSearchImpl muzofondMusic,
                                  KrolikSaitServiceImpl krolikMusic) throws IOException {
        this.vkMusic = vkMusic;
        this.zaycevMusic = zaycevMusic;
        this.muzofondMusic = muzofondMusic;
        this.krolikMusic = krolikMusic;
    }


    @Override
    public Track getSong(String author, String song) throws IOException {
        List<? extends DownloadMusicService> listServices = new ArrayList<>(Arrays.asList(zaycevMusic, vkMusic, krolikMusic, muzofondMusic));
        for (DownloadMusicService service : listServices) {
            track = service.getSong(author, song);
            if(track != null) break;
        }
        return track;
    }

}
