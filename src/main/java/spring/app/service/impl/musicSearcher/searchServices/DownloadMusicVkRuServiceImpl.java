package spring.app.service.impl.musicSearcher.searchServices;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import spring.app.service.abstraction.DownloadMusicService;
import spring.app.service.entity.Track;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.concurrent.CompletableFuture;

@Service("downloadMusicVkRuServiceImpl")
public class DownloadMusicVkRuServiceImpl implements DownloadMusicService {
    private final static Logger LOGGER = LoggerFactory.getLogger(DownloadMusicVkRuServiceImpl.class);

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
     * Минимальный размер файла для скачивания. Указан в application.properties.
     */
    @Value("${mp3.min.filesize}")
    private int minFileSize;

    /**
     * Метод для создания поискового запроса на данный музыкальный сервис и
     * получения ссылки на скачивания трека.
     *
     * @param author - имя исполнителя
     * @param song   - название песни
     * @return ссылка на скачивание .mp3 файла.
     */
    public String searchSong(String author, String song) throws IOException {
        LOGGER.debug("Поиск трека: {} - {} c DownloadMusicVK.ru...", author, song);
        String searchUrl = "https://downloadmusicvk.ru/audio/search?q=";
        String link = "";

        try {
            Document document = Jsoup.connect(searchUrl + author + " " + song).get();

            Element div = document.getElementsByClass("btn btn-primary btn-xs download").get(0);
            String songUrl = div.attr("href");   // url  в котором хранится ссылка для скачивания

            String[] fragments = songUrl.substring(19).split("&");

            for (String path : fragments) {
                if (path.contains("artist")) {
                    authorName = URLDecoder.decode(path.split("=")[1], "UTF-8");
                    authorName = URLDecoder.decode(author, "UTF-8");
                }
                if (path.contains("title")) {
                    songName = URLDecoder.decode(path.split("=")[1], "UTF-8");
                    songName = URLDecoder.decode(song, "UTF-8");
                }
            }
            trackName = author + "-" + song;

            link = "https://downloadmusicvk.ru/audio/download?" +
                    fragments[1] + "&" + fragments[2] + "&" + fragments[5] + "&" + fragments[0];
        } catch (Exception e) {
            LOGGER.debug("Поиск трека: {} - {} c DownloadMusicVK.ru неуспешен! :(", author, song);
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
    @Async
    public CompletableFuture<Track> getSong(String author, String song) {
        try {
            String link = searchSong(author, song);
            LOGGER.debug("Скачивание трека: {} - {} c DownloadMusicVK.ru via {}...", author, song, link);
            URL obj = new URL(link);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestProperty("User-Agent", "Mozilla/5.0");
            int responseCode = con.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {

                InputStream in = con.getInputStream();
                ByteArrayOutputStream buffer = new ByteArrayOutputStream();
                int nRead;
                byte[] data = new byte[1024 * 10];
                while ((nRead = in.read(data, 0, data.length)) != -1) {
                    buffer.write(data, 0, nRead);
                }
                buffer.flush();
                in.close();

                byte[] track = buffer.toByteArray();
                if (track.length < minFileSize) {
                    return CompletableFuture.completedFuture(null);    //если песня меньше заданного размера, возвращаем null
                }
                return CompletableFuture.completedFuture(new Track(authorName, songName, trackName, track));
            }
        } catch (Exception e) {
            LOGGER.debug("Скачивание трека: {} - {} c DownloadMusicVK.ru неуспешно! :(", author, song);
        }
        return CompletableFuture.completedFuture(null);
    }
}