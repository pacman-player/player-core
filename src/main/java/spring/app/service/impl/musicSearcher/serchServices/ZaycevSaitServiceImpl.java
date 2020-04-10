package spring.app.service.impl.musicSearcher.serchServices;

import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import spring.app.service.abstraction.DownloadMusicService;
import spring.app.service.entity.Track;

import java.net.HttpURLConnection;
import java.net.URL;

@Service("zaycevSaitServiceImpl")
@Transactional
public class ZaycevSaitServiceImpl implements DownloadMusicService {
    private final static Logger LOGGER = LoggerFactory.getLogger(ZaycevSaitServiceImpl.class);
    private RestTemplate restTemplate = new RestTemplate();

    /**
     * Имя исполнителя, полученное от сервиса.
     */
    private String authorName;

    /**
     * Название трека, полученное от сервиса.
     */
    private String songName;

    /**
     * Составное полное имя трека, состоящее из {@link #songName} и  {@link #authorName}.
     */
    private String trackName;

    /**
     * Метод для создания поискового запроса на данный музыкальный сервис и
     * получения ссылки на скачивания трека.
     *
     * @param author - имя исполнителя
     * @param song   - название песни
     * @return ссылка на скачивание .mp3 файла.
     */
    public String searchSong(String author, String song) {
        LOGGER.debug("Поиск трека: {} - {} c Zaytsev.net...", author, song);
        String baseUrl = "https://zaycev.net";
        String searchUrl = "https://zaycev.net/search.html?query_search=";
        String link = "";

        try {
            Document document = Jsoup.connect(searchUrl + author + " " + song).get();

            Element div = document.getElementsByClass("musicset-track clearfix ").get(0);
            String jsonUrl = div.attr("data-url");   // url на json в котором хранится ссылка для скачивания
            Element div2 = div.getElementsByClass("musicset-track__fullname").get(0);

            trackName = div2.attr("title");  // вытаскиваем исполнителя и название песни
            if (trackName.contains(" – ")) {
                String[] perfomerAndSong = trackName.split(" – ");
                authorName = perfomerAndSong[0];
                songName = perfomerAndSong[1];
                trackName = trackName.replaceAll(" – ", "-");
            } else {
                authorName = author;
                songName = song;
                trackName = authorName + "-" + songName;
            }

            String json = Jsoup.connect(baseUrl + jsonUrl).ignoreContentType(true).execute().body();
            JSONObject jsonObj = new JSONObject(json);
            link = jsonObj.getString("url");
        } catch (Exception e) {
            LOGGER.debug("Поиск трека: {} - {} c Zaytsev.net неуспешен! :(", author, song);
        }
        return link;  // ссылка на скачивание
    }

    /**
     * Основной метод получения искомого трека.
     * Запускает метод {@link #searchSong} для создания поискового запроса на данный музыкальный сервис и
     * получения ссылки на скачивания трека. Затем происходит скачивание трека и сохранение его в
     * директорию с музыкой в формате songId.mp3.
     *
     * @param author - имя исполнителя
     * @param song   - название песни
     * @return возвращение нового экземпляра класса Track.
     */
    @Override
    public Track getSong(String author, String song) {
        try {
            String link = searchSong(author, song);
            LOGGER.debug("Скачивание трека: {} - {} c Zaytsev.net via {}...", author, song, link);
            URL obj = new URL(link);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestProperty("User-Agent", "Mozilla/5.0");
            int responseCode = con.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                byte[] track = restTemplate.getForObject(link, byte[].class);
                if (track.length < 2 * 1024 * 1024) {   //проверяем, что песня более 2 Мб
                    return null;    //если песня меньше заданного размера, возвращаем null
                }
                return new Track(authorName, songName, trackName, track);
            }
        } catch (Exception e) {
            LOGGER.debug("Скачивание трека: {} - {} c Zaytsev.net неуспешно! :(", author, song);
        }
        return null;
    }
}
