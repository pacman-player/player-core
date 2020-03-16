package spring.app.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import spring.app.service.abstraction.DownloadMusicService;
import spring.app.service.impl.musicSearcher.MusicSearchServiceImpl;

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
//@PropertySource("classpath:application.properties")
public class DownloadMusicServiceFactory{
    /**
     * Имя первого сервиса для поиска
     */
//    @Value("${music.searchService.one}")
    String one = "muzofondfmMusicSearchImpl";
    /**
     * Имя второго сервиса для поиска
     */
//    @Value("${music.searchService.two}")
    String two = "zaycevSaitServiceImpl";
    /**
     * Имя третьего сервиса для поиска
     */
//    @Value("${music.searchService.three}")
    String three = "krolikSaitServiceImpl";
    /**
     * Имя четвертого сервиса для поиска
     */
//    @Value("${music.searchService.four}")
    String four = "downloadMusicVkRuServiceImpl";

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

    private List<DownloadMusicService> services = new ArrayList<>();


    public DownloadMusicServiceFactory() {
    }


    public String getOne() {
        return one;
    }

    public void setOne(String one) {
        this.one = one;
    }

    public String getTwo() {
        return two;
    }

    public void setTwo(String two) {
        this.two = two;
    }

    public String getThree() {
        return three;
    }

    public void setThree(String three) {
        this.three = three;
    }

    public String getFour() {
        return four;
    }

    public void setFour(String four) {
        this.four = four;
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
     * в классе {@link MusicSearchServiceImpl}
     */
    public List<DownloadMusicService> getDownloadServices() {
        try {
            services.clear();
            services.add(getService(one));
            services.add(getService(two));
            services.add(getService(three));
            services.add(getService(four));
        } catch (Exception e) { // На случай ошибок в файле конфигурации, возвращаем очередность по-умолчанию
            services.clear();
            services.add(zaycevSaitServiceImpl);
            services.add(muzofondfmMusicSearchImpl);
            services.add(krolikSaitServiceImpl);
            services.add(downloadMusicVkRuServiceImpl);
        }
        return services;
    }

}