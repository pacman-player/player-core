package spring.app.service.impl;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import spring.app.service.abstraction.MuzofondfmMusicSearch;
import spring.app.service.entity.Track;
import spring.app.util.PlayerPaths;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;

@Service
@Transactional
public class MuzofondfmMusicSearchImpl implements MuzofondfmMusicSearch {
    private final Logger LOGGER = LoggerFactory.getLogger(MuzofondfmMusicSearchImpl.class);

    @Override
    public List<Track> searchSong(String author, String track) {
        final String url = "https://muzofond.fm/search/";
        List<Track> trackList = null;
        try {
            Document document = Jsoup.connect(String.format("%s%s %s", url, author, track)).get();
            Elements list = document.getElementsByClass("item");
            trackList = new LinkedList<>();
            int index = 0;
            for (Element element : list) {
                String trackUrl = element.getElementsByClass("play").attr("data-url");
                String trackArtist = element.getElementsByClass("artist").text();
                String trackName = element.getElementsByClass("track").text();
                if (!trackArtist.isEmpty() && !trackName.isEmpty() && !trackUrl.isEmpty()) {
                    trackList.add(new Track(index++, trackArtist, trackName, trackUrl));
                }
            }
            LOGGER.info("{} songs found for request \'{} - {}\'", trackList.size(), author, track);
        } catch (IOException ex) {
            LOGGER.error(ex.getMessage(), ex);
        }
        return trackList;
    }

    @Override
    public byte[] getSong(Track track) {
        byte[] bytes = null;
        try {
            bytes = (new RestTemplate()).getForObject(track.getLink(), byte[].class);
            Path path = PlayerPaths.getSongsDir(String.format("%s %s.mp3", track.getAuthor(), track.getTrack()));
            if (path != null) {
                Files.write(path, bytes);
            }
            LOGGER.info("Downloaded track \'{} - {}\'", track.getAuthor(), track.getTrack());
        } catch (IOException ex) {
            LOGGER.error(ex.getMessage(), ex);
        }
        return bytes;
    }
}
