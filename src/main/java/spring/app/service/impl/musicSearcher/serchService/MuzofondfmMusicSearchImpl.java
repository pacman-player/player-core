package spring.app.service.impl.musicSearcher.serchService;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import spring.app.service.abstraction.DownloadMusicService;
import spring.app.util.PlayerPaths;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
@Transactional
public class MuzofondfmMusicSearchImpl implements DownloadMusicService {
    private final Logger LOGGER = LoggerFactory.getLogger(MuzofondfmMusicSearchImpl.class);
    private String trackName = "";

    @Override
    public String searchSong(String author, String song) {

        final String url = "https://muzofond.fm/search/";
        Document document = null;
        String link = "";

        try {
            document = Jsoup.connect(String.format("%s%s %s", url, author, song)).get();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error search on muzofond");
        }
        Element first = document.getElementsByClass("item").first();

        link = first.getElementsByClass("play").attr("data-url");
        author = first.getElementsByClass("artist").text();
        song = first.getElementsByClass("track").text();
        trackName = author + " - " + song;

        LOGGER.info("{} songs found for request \'{} - {}\'", url, author, song);
        return link;
    }

    @Override
    public byte[] getSong(String author, String song) {
        byte[] track = null;
        track = (new RestTemplate()).getForObject(searchSong(author, song), byte[].class);

        Path path = PlayerPaths.getSongsDir(getTrackName() + ".mp3");
        if (path != null) {
            try {
                Files.write(path, track);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        LOGGER.info("Downloaded track \'{} - {}\'", getTrackName());

        return track;
    }

    @Override
    public String getTrackName() {
        return trackName;
    }
}
