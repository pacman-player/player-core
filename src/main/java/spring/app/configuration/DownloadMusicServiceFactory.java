package spring.app.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import spring.app.service.abstraction.DownloadMusicService;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс, в котором реализована логика выбора очередности музыкальных сервисов в Runtime из
 * файла application.properties. При каждом запросе, считываются значения из конфигурации и
 * формируется список сервисов в методе {@link #getService(String)}, который используется в
 * поиске трека в классе {@link spring.app.service.impl.musicSearcher.MusicSearchServiceImpl}
 */
@Configuration
@ComponentScan("spring.app")
@PropertySource("classpath:application.properties")
public class DownloadMusicServiceFactory {
    /**
     * Имя первого сервиса для поиска
     */
    @Value("${music.searchService.one}")
    String one;
    /**
     * Имя второго сервиса для поиска
     */
    @Value("${music.searchService.two}")
    String two;
    /**
     * Имя третьего сервиса для поиска
     */
    @Value("${music.searchService.three}")
    String three;
    /**
     * Имя четвертого сервиса для поиска
     */
    @Value("${music.searchService.four}")
    String four;

    @Autowired
    @Qualifier("zaycevSaitServiceImpl")
    private DownloadMusicService zaycevSaitServiceImpl;

    @Autowired
    @Qualifier("muzofondfmMusicSearchImpl")
    private DownloadMusicService muzofondfmMusicSearchImpl;

    @Autowired
    @Qualifier("krolikSaitServiceImpl")
    private DownloadMusicService krolikSaitServiceImpl;

    @Autowired
    @Qualifier("downloadMusicVkRuServiceImpl")
    private DownloadMusicService downloadMusicVkRuServiceImpl;


    public DownloadMusicServiceFactory() {
    }

    /** Метод для получения нужной имплементации музыкального сервиса.
     * @param implName - значение имени сервиса в файле application.properties
     */
    public DownloadMusicService getService(String implName) {
        DownloadMusicService dms = null;

        switch (implName) {
            case "zaycevSaitServiceImpl":
                dms = zaycevSaitServiceImpl;
                break;
            case "muzofondfmMusicSearchImpl":
                dms = muzofondfmMusicSearchImpl;
                break;
            case "krolikSaitServiceImpl":
                dms = krolikSaitServiceImpl;
                break;
            case "downloadMusicVkRuServiceImpl":
                dms = downloadMusicVkRuServiceImpl;
                break;
        }
        return dms;
    }

    /** Метод, формирующий список сервисов, который поочередно используется в поиске трека
     * в классе {@link spring.app.service.impl.musicSearcher.MusicSearchServiceImpl}
     */
    public List<DownloadMusicService> getDownloadServices() {
        List<DownloadMusicService> list = new ArrayList<>();
        list.add(getService(one));
        list.add(getService(two));
        list.add(getService(three));
        list.add(getService(four));
        return list;
    }

}