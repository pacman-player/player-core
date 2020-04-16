package spring.app.service.impl.musicSearcher.serchServices;

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

import java.io.IOException;

@Service("krolikSaitServiceImpl")
@Transactional
public class KrolikSaitServiceImpl implements DownloadMusicService {
    private final static Logger LOGGER = LoggerFactory.getLogger(KrolikSaitServiceImpl.class);
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
    public String searchSong(String author, String song) throws IOException {
        LOGGER.debug("Поиск трека: {} - {} c Krolik.biz...", author, song);
        final String url = "https://krolik.biz/search/?q=";
        String link = "";

        try {
            Document document = Jsoup.connect(String.format("%s%s %s", url, author, song)).get();
            Element first = document.getElementsByAttributeValue("class", "mp3").first();
            link = first.getElementsByClass("btn play").attr("data-url");

            authorName = first.getElementsByClass("artist_name").text();
            songName = first.getElementsByClass("song_name").text();
            trackName = author + "-" + song;
        } catch (Exception e) {
            LOGGER.debug("Поиск трека: {} - {} c Krolik.biz неуспешен! :(", author, song);
        }
        return link;
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
            LOGGER.debug("Скачивание трека: {} - {} c Krolik.biz via {}...", author, song, link);

            byte[] track = restTemplate.getForObject(link, byte[].class);
            if (track.length < 2 * 1024 * 1024) {   //проверяем, что песня более 2 Мб
                return null;    //если песня меньше заданного размера, возвращаем null
            }
            return new Track(authorName, songName, trackName, track);
        } catch (Exception e) {
            LOGGER.debug("Скачивание трека: {} - {} c Krolik.biz неуспешно! :(", author, song);
        }
        return null;
    }
}
