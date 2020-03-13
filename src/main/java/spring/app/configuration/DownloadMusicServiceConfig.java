package spring.app.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import spring.app.service.abstraction.DownloadMusicService;

import java.util.ArrayList;
import java.util.List;

@Configuration
@ComponentScan("spring.app")
@PropertySource("classpath:application.properties")
public class DownloadMusicServiceConfig {

    @Value("${music.searchService.one}")
    String one;

    @Value("${music.searchService.two}")
    String two;

    @Value("${music.searchService.three}")
    String three;

    @Value("${music.searchService.four}")
    String four;

    @Autowired
    DownloadMusicServiceFactory factory;

    public DownloadMusicServiceConfig(/*DownloadMusicServiceFactory factory*/) {
//        this.factory = factory;
    }

    public List<DownloadMusicService> getDownloadServices() {
        List<DownloadMusicService> list = new ArrayList<>();
        list.add(factory.getService(one));
        list.add(factory.getService(two));
        list.add(factory.getService(three));
        list.add(factory.getService(four));
        return list;
    }

}