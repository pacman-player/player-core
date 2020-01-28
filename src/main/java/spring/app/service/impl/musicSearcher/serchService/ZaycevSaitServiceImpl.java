package spring.app.service.impl.musicSearcher.serchService;

import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import spring.app.service.abstraction.DownloadMusicService;
import spring.app.util.PlayerPaths;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
@Transactional
public class ZaycevSaitServiceImpl implements DownloadMusicService {

    private RestTemplate restTemplate = new RestTemplate();
    private String trackName = "";

    @Override
    public String searchSong(String author, String song) {

        String baseUrl = "https://zaycev.net";
        String searchUrl = "https://zaycev.net/search.html?query_search=";
        Document document = null;
        String link = "";

        try {
            document = Jsoup.connect(searchUrl + author + " " + song).get();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error search on zaycev.net");
        }

        Element div = document.getElementsByClass("musicset-track clearfix ").get(0);
        String jsonUrl = div.attr("data-url");   // url на json в котором хранится ссылка для скачивания
        Element div2 = div.getElementsByClass("musicset-track__fullname").get(0);
        trackName = div2.attr("title");  // вытаскиваем исполнителя и название песни

        try {
            String json = Jsoup.connect(baseUrl + jsonUrl).ignoreContentType(true).execute().body();
            JSONObject jsonObj = new JSONObject(json);
            link = jsonObj.getString("url");
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
        return link;  // ссылка на скачивание
    }


    @Override
    public byte[] getSong(String author, String song) throws IOException {
        String link = searchSong(author, song);
        byte[] track = null;
        URL obj = new URL(link);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestProperty("User-Agent", "Mozilla/5.0");
        int responseCode = con.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            track = restTemplate.getForObject(link, byte[].class);
        }
        Path path = PlayerPaths.getSongsDir(getTrackName() + ".mp3");
        if (path != null) {
            try {
                Files.write(path, track);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return track;
    }

    public String getTrackName() {
        return trackName;
    }

}
