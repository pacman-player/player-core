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

/**
 * Класс определяет жанр песни посредством запроса в поисковик google.com
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
        String url = String.format(googleEn, trackAuthor);
        String genre = "unidentified";
        Document doc;

        try {
            LOGGER.debug("Finding genre for '{}'. Google searching for '{}'", trackAuthor, url);
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

//        if (genre.equals("unidentified")) {
//            return getGenreLastFm(trackAuthor, trackSong); //если поиск жанра не удался через google.ru, берем два топовых тега из ласт.фм
//        }
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
        List<GenreDto> list = genreService.getAllGenreDto();
        Set<String> temp = new HashSet<>();
        String[] genres = genre.toLowerCase().split("[\\s-/]");
        for (GenreDto dto : list) {
            for (String s : genres) {
                if (dto.getKeywords().contains(s)) {
                    temp.add(dto.getName());
                    break;
                }
            }
        }
        return temp.toArray(new String[0]);
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
