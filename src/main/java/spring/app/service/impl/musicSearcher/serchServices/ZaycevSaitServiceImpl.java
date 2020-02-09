package spring.app.service.impl.musicSearcher.serchServices;

import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import spring.app.service.abstraction.DownloadMusicService;
import spring.app.service.entity.Track;
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

    private String authorName;
    private String songName;
    private String trackName = "";
    private String link = "";
    private String[] songInfo = new String[2];

    @Override
    public String[] searchSong(String author, String song) {

        String baseUrl = "https://zaycev.net";
        String searchUrl = "https://zaycev.net/search.html?query_search=";
        Document document = null;

        try {
            document = Jsoup.connect(searchUrl + author + " " + song).get();

            Element div = document.getElementsByClass("musicset-track clearfix ").get(0);
            String jsonUrl = div.attr("data-url");   // url на json в котором хранится ссылка для скачивания
            Element div2 = div.getElementsByClass("musicset-track__fullname").get(0);

            trackName = div2.attr("title");  // вытаскиваем исполнителя и название песни
            if (trackName.contains(" – ")) {
                String[] perfomerAndSong = trackName.split(" – ");
                authorName = perfomerAndSong[0];
                songName = perfomerAndSong[1];
            }

            String json = Jsoup.connect(baseUrl + jsonUrl).ignoreContentType(true).execute().body();
            JSONObject jsonObj = new JSONObject(json);
            link = jsonObj.getString("url");

        } catch (Exception e) {
            e.printStackTrace();
        }
        songInfo[0] = trackName;
        songInfo[1] = link;
        return songInfo;
    }


    @Override
    public Track getSong(String author, String song) throws IOException {
        try {
            String link = searchSong(author, song)[1];
            URL obj = new URL(link);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestProperty("User-Agent", "Mozilla/5.0");
            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                byte[] track = restTemplate.getForObject(link, byte[].class);

                if (track.length > 1024 * 10) {    //проверка что песня полноценная

                    Path path = PlayerPaths.getSongsDir(trackName + ".mp3");
                    if (path != null) {
                        try {
                            Files.write(path, track);  //записываем песню с директорию
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                } else return null;  //если песня меньше 2мб возвращаем 0
                return new Track(authorName, songName, trackName, track);
            }
        } catch (Exception e) {
            System.out.println("Ошибка скачивания с zaycev.net");
        }
        return null;
    }
}
