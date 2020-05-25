package spring.app.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import spring.app.dto.GenreDto;
import spring.app.service.abstraction.GenreDefinerService;
import spring.app.service.abstraction.GenreService;

import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Класс определяет жанр песни посредством запроса в поисковик google.com
 * если не находит то обращается music.yandex.com
 */
@Service
public class GenreDefinerServiceImpl implements GenreDefinerService {
    private final static Logger LOGGER = LoggerFactory.getLogger(GenreDefinerServiceImpl.class);
    private final RestTemplate restTemplate;
    private final GenreService genreService;

    @Value("${lastfm.url.getToptags}")
    private String getTopTags;

    @Value("${google.search.genre.en}")
    private String googleEn;

    @Autowired
    public GenreDefinerServiceImpl(RestTemplate restTemplate, GenreService genreService) {
        this.restTemplate = restTemplate;
        this.genreService = genreService;
    }


    @Override
    public String[] defineGenre(String trackAuthor, String trackSong) throws IOException {
        String trackName = trackAuthor + " - " + trackSong;
        String url = String.format(googleEn, trackName);
        String genre = "unidentified";
        Document doc;

        try {
            LOGGER.debug("Finding genre for '{}'. Google searching for '{}'", trackName, url);
            doc = Jsoup
                    .connect(url)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.117 YaBrowser/20.2.0.1043 Yowser/2.5 Safari/537.36")
                    .header("Accept-Language", "ru")
                    .header("Accept-Encoding", "gzip,deflate,br")
                    .timeout(10000).get();

            genre = doc.getElementsByClass("Z0LcW").first().text();
        } catch (HttpStatusException se) {
            LOGGER.info("Google has banned us for suspicious activity, moving on to other searches...");
        } catch (NullPointerException e) {
            LOGGER.debug("Didn't find anything, caught NullPointerException!");
        }
        LOGGER.debug("Genre result found so far = {}", genre);

        if (genre.equals("unidentified")) {
            return getGenreLastFm(trackAuthor, trackSong); //если поиск жанра не удался через google.ru, берем два топовых тега из ласт.фм
        }
        LOGGER.debug("Final search result is = {}", genre);

        return filterGenres(genre); // фильтр от "мусора"
    }

    /**
     * Возможны несколько стилей у одной песни.
     * Поисковики выдают различные ответы с различными приписками к жанрам
     * Очищаем от "мусора" и возвращаем массив строк с названием жанров
     *
     * @param genre
     * @return
     */
    private String[] filterGenres(String genre) {
        LOGGER.debug("Extracting Genres[] out of string = {}", genre);
        genre = genre.replaceAll("\\s", "");
        LOGGER.debug("  1) String = {}", genre);
        Pattern pattern = Pattern.compile("иностранный|русский|иностранная|-музыка|музыка");

        Matcher matcher = pattern.matcher(genre);
        LOGGER.debug("  2) Matcher is = {}", matcher);
        StringBuffer buffer = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(buffer, "");
        }
        matcher.appendTail(buffer);
        LOGGER.debug("  3) Matcher is = {}", matcher);

        pattern = Pattern.compile("/|,");
        String[] genres = pattern.split(buffer);
        LOGGER.debug("  4) Resulting Genres[] are = {}", Arrays.asList(genres));

        return genres;
    }

    /**
     * Получение данных о жанре композиции с помощью last.fm api, используя
     * метод track.getTopTags: получаем ответ с телом в формате json.
     * Заголовок "user-agent" и Thread.sleep() позволяет снизить шанс бана.
     */
    private String[] getGenreLastFm(String trackAuthor, String trackSong) throws IOException {
        List<String> genreTags = new ArrayList<>();
        HttpHeaders headers = new HttpHeaders();
        headers.add("user-agent", "pacman_player");
        HttpEntity request = new HttpEntity(headers);
//        Thread.sleep(250);
        ResponseEntity<String> response = restTemplate.exchange(getTopTags, HttpMethod.GET, request, String.class, trackAuthor, trackSong);
        // берем из json имена первых 2 тегов, которые в 95% случаев являются жанрами композиции
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(response.getBody());
        JsonNode tagsFields = root.get("toptags").get("tag");
        if (tagsFields.size() > 0) {
            try {
                Iterator<JsonNode> elements = tagsFields.elements();
                for (int i = 0; i < 2; i++) {
                    JsonNode next = elements.next();
                    genreTags.add(next.get("name").asText());
                }
            } catch (NoSuchElementException e) {
                // у малопопулярных треков может быть меньше двух тегов
            }
        } else {
            genreTags.add("unidentified");
        }
        LOGGER.debug("Resulting Genres[] are = {}", genreTags);
        return genreTags.toArray(new String[0]);
    }

}
