package spring.app.configuration;

import org.springframework.stereotype.Component;
import spring.app.service.impl.musicSearcher.MusicSearchServiceImpl;

import java.sql.Timestamp;

/**
 * Класс, в котором реализована логика выбора очередности музыкальных сервисов в Runtime из
 * файла application.properties. При каждом запросе, считываются значения из конфигурации и
 * формируется список сервисов в методе {@link #getService(String)}, который используется в
 * поиске трека в классе {@link MusicSearchServiceImpl}
 */
//@Configuration
//@ComponentScan("spring.app")
//@PropertySource("classpath:application.properties")
@Component
public class DownloadMusicServiceConfigurer implements DownloadMusicServiceConfigurerMBean {

//    /**
//     * Имя первого сервиса для поиска
//     */
////    @Value("${music.searchService.one}")
//    String one = "muzofondfmMusicSearchImpl";
//    /**
//     * Имя второго сервиса для поиска
//     */
////    @Value("${music.searchService.two}")
//    String two = "zaycevSaitServiceImpl";
//    /**
//     * Имя третьего сервиса для поиска
//     */
////    @Value("${music.searchService.three}")
//    String three = "krolikSaitServiceImpl";
//    /**
//     * Имя четвертого сервиса для поиска
//     */
////    @Value("${music.searchService.four}")
//    String four = "downloadMusicVkRuServiceImpl";
//
//    @Autowired
//    @Qualifier("zaycevSaitServiceImpl")
//    private DownloadMusicService zaycevSaitServiceImpl;
//
//    @Autowired
//    @Qualifier("muzofondfmMusicSearchImpl")
//    private DownloadMusicService muzofondfmMusicSearchImpl;
//
//    @Autowired
//    @Qualifier("krolikSaitServiceImpl")
//    private DownloadMusicService krolikSaitServiceImpl;
//
//    @Autowired
//    @Qualifier("downloadMusicVkRuServiceImpl")
//    private DownloadMusicService downloadMusicVkRuServiceImpl;
//
//    private List<DownloadMusicService> services = new ArrayList<>();
//

    private DownloadMusicServiceFactory factory;


    public DownloadMusicServiceConfigurer(DownloadMusicServiceFactory factory) {
        this.factory = factory;
    }


    public String getOne() {
        return factory.getOne();
    }

    public void setOne(String one) {
        System.out.println("##################  setting Service ONE !!!!  ####################" + one);
        System.out.println(new Timestamp(System.currentTimeMillis()));
        this.factory.setOne(one);
    }

    public String getTwo() {
        return factory.getTwo();
    }

    public void setTwo(String two) {
        this.factory.setTwo(two);
    }

    public String getThree() {
        return factory.getThree();
    }

    public void setThree(String three) {
        this.factory.setThree(three);
    }

    public String getFour() {
        return factory.getFour();
    }

    public void setFour(String four) {
        this.factory.setFour(four);
    }

}