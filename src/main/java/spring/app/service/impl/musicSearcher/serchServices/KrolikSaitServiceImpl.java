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
import spring.app.util.PlayerPaths;

import java.io.IOException;
import java.nio.file.Path;

@Service
@Transactional
public class KrolikSaitServiceImpl implements DownloadMusicService {
    private final static Logger LOGGER = LoggerFactory.getLogger(KrolikSaitServiceImpl.class);
    private RestTemplate restTemplate = new RestTemplate();
    private String authorName;
    private String songName;
    private String trackName;


    public String searchSong(String author, String song) throws IOException {
        LOGGER.debug("Поиск трека: {} - {} c Krolik.biz...", author, song);
        final String url = "https://krolik.biz/search/?q=";
        Document document = null;
        String link = "";

        try {
            document = Jsoup.connect(String.format("%s%s %s", url, author, song)).get();

            Element first = document.getElementsByAttributeValue("class", "mp3").first();

            link = first.getElementsByClass("btn play").attr("data-url");
            author = first.getElementsByClass("artist_name").text();
            song = first.getElementsByClass("song_name").text();

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
     * @param author - имя исполнителя
     * @param song - название песни
     * @return возвращение нового экземпляра класса Track.
     */
    @Override
    public Track getSong(String author, String song) throws IOException {
        try {
            String link = searchSong(author, song);
            LOGGER.debug("Скачивание трека: {} - {} c Krolik.biz via {}...", author, song, link);

            byte[] track = restTemplate.getForObject(link, byte[].class);
            Path path = null;
            if (track.length > 1024 * 20) {    //проверка что песня полноценная

                path = PlayerPaths.getSongsDir(trackName + ".mp3");
//                if (path != null) {
//                    try {
//                        Files.write(path, track);  //записываем песню с директорию
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
            } else return null;  //если песня меньше 2мб возвращаем 0
            return new Track(authorName, songName, trackName, track, path);
        } catch (Exception e) {
            LOGGER.debug("Скачивание трека: {} - {} c Krolik.biz неуспешно! :(", author, song);
        }
        return null;
    }
}
