package spring.app.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import spring.app.service.abstraction.DownloadMusicService;

@Component
public class DownloadMusicServiceFactory {

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

    public DownloadMusicService getService(String implName) {
        DownloadMusicService dms = null;

        switch (implName) {
            case "zaycevSaitServiceImpl" :
                dms = zaycevSaitServiceImpl;
                break;
            case "muzofondfmMusicSearchImpl" :
                dms = muzofondfmMusicSearchImpl;
                break;
            case "krolikSaitServiceImpl" :
                dms = krolikSaitServiceImpl;
                break;
            case "downloadMusicVkRuServiceImpl" :
                dms = downloadMusicVkRuServiceImpl;
                break;
        }
        return dms;
    }

}
