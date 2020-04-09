package spring.app.service.impl.musicSearcher.serchServices;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import spring.app.service.abstraction.DownloadMusicService;
import spring.app.service.entity.Track;

/**
 * Класс для поиска и скачивания песен с сервиса <i>muzofond.fm</i>.
 */
@Service("muzofondfmMusicSearchImpl")
@Transactional
public class MuzofondfmMusicSearchImpl implements DownloadMusicService {
    private final static Logger LOGGER = LoggerFactory.getLogger(MuzofondfmMusicSearchImpl.class);
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
        LOGGER.debug("Поиск трека: {} - {} c Muzofond.fm...", author, song);

        /** Строка для поиска на данном музыкальном сервисе. */
        final String url = "https://muzofond.fm/search/";

        /** Строка для скачивания .mp3 файла. */
        String link = "";

        try {
//          Создаем поисковый запрос к сервису
            Document document = Jsoup.connect(String.format("%s%s %s", url, author, song)).get();
//          У данного сервиса находим все элементы с найденными песнями
            Elements items = document.select(".item");
            if (items.size() > 0) { // Если песни были найдены,
                Element fullSongElement = null;
//              Проходим по списку элементов и берем первый элемент, у которого продожительность
//              трека более минуты (чтобы избежать скачивание превью-версий треков)
                for (Element element : items) {
                    if (fullSongElement == null
                            && Integer.parseInt(element.attr("data-duration")) > 60) {
                        fullSongElement = element;
                    }
                }
//              Если песня была найдена, шанс, что будет только обрезанная версия, но не оригинал,
//              т.е. шанс NPE, минимален. В любом случае, он будет перехвачен и поиск продолжится на другом сервисе.
                link = fullSongElement.getElementsByClass("play").attr("data-url");
                authorName = fullSongElement.getElementsByClass("artist").text();
                songName = fullSongElement.getElementsByClass("track").text();
                trackName = author + "-" + song;
            }
        } catch (Exception e) {
            LOGGER.debug("Поиск трека: {} - {} c Muzofond.fm неуспешен! :(", author, song);
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
            LOGGER.debug("Скачивание трека: {} - {} c Muzofond.fm via {}...", author, song, link);

            byte[] track = restTemplate.getForObject(link, byte[].class);
            if (track.length < 2 * 1024 * 1024) {   //проверяем, что песня более 2 Мб
                return null;    //если песня меньше заданного размера, возвращаем null
            }
            return new Track(authorName, songName, trackName, track);
        } catch (Exception e) {
            LOGGER.debug("Скачивание трека: {} - {} c Muzofond.fm неуспешно! :(", author, song);
        }
        return null;
    }

}
