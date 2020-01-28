package spring.app.service.impl.musicSearcher.serchService;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
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
public class KrolikSaitServiceImpl implements DownloadMusicService {

    private RestTemplate restTemplate = new RestTemplate();
    private String trackName;

    @Override
    public String searchSong(String author, String song) throws IOException {

        final String url = "https://krolik.biz/search/?q=";
        Document document = null;
        String link = "";

        try {
            document = Jsoup.connect(String.format("%s%s %s", url, author, song)).get();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error search on muzofond");
        }
        Element first = document.getElementsByAttributeValue("class", "mp3").first();

        link = first.getElementsByClass("btn play").attr("data-url");
        author = first.getElementsByClass("artist_name").text();
        song = first.getElementsByClass("song_name").text();

        trackName = author + " - " + song;

        return link;
    }


    @Override
    public byte[] getSong(String author, String song) throws IOException {
        String link = searchSong(author, song);
        byte[] bytes = restTemplate.getForObject(link, byte[].class);

        Path path = PlayerPaths.getSongsDir(getTrackName() + ".mp3");
        if (path != null) {
            try {
                Files.write(path, bytes);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return bytes;
    }

    @Override
    public String getTrackName() {
        return trackName;
    }
}
