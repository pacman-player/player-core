package spring.app.service.impl;

import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import spring.app.dto.GenreDto;
import spring.app.service.abstraction.GenreDefinerService;
import spring.app.service.abstraction.GenreService;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Класс определяет жанр песни посредством поиска ключевых
 * слов, присущих жанру в google.com
 * если не находит то обращается music.yandex.com
 */
@Service
public class GenreDefinerServiceImpl implements GenreDefinerService {
    private final static Logger LOGGER = LoggerFactory.getLogger(GenreDefinerServiceImpl.class);
    private final GenreService genreService;

    @Value("${google.search.genre}")
    private String googleSearch;

    @Value("${yandex.search.genre}")
    private String yandexSearch;

    @Autowired
    public GenreDefinerServiceImpl(GenreService genreService) {
        this.genreService = genreService;
    }


    @Override
    public String[] defineGenre(String authorName) throws IOException {
        String url = String.format(googleSearch, authorName);
        String genre = "Неизвестный жанр";
        Document doc;

        try {
            LOGGER.debug("Finding genre for '{}'. Google searching for '{}'", authorName, url);
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
        if (genre.equals("Неизвестный жанр")) {
            genre = defineGenreInYandex(authorName);
        }
        String[] result = getGenres(genre);
        return result; //присвоение жанров по ключевым словам
    }

    private String defineGenreInYandex(String authorName) throws IOException {
        String url = String.format(yandexSearch, authorName);
        String genre = "Неизвестный жанр";
        Document doc;

        try {
            LOGGER.debug("Finding genre for '{}'. Yandex searching for '{}'", authorName, url);
            doc = Jsoup
                    .connect(url)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.117 YaBrowser/20.2.0.1043 Yowser/2.5 Safari/537.36")
                    .header("Accept-Language", "ru")
                    .header("Accept-Encoding", "gzip, deflate, br")
                    .timeout(10000).get();

            genre = doc.getElementsByClass("d-genres").first().text();
        } catch (HttpStatusException | NullPointerException e) {
            LOGGER.debug("Didn't find anything, caught NullPointerException!");
        }
        LOGGER.debug("Genre result found so far = {}", genre);

        if (genre.equals("Неизвестный жанр")) {
            String artistName = authorName.split(" – ")[0];
            url = "https://music.yandex.com/search?text=" + artistName + "&type=artists";

            try {
                LOGGER.debug("Finding genre for '{}'. Yandex searching for '{}'", authorName, url);
                doc = Jsoup
                        .connect(url)
                        .userAgent("Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.117 YaBrowser/20.2.0.1043 Yowser/2.5 Safari/537.36")
                        .header("Accept-Language", "ru")
                        .header("Accept-Encoding", "gzip, deflate, br")
                        .timeout(10000).get();

                genre = doc.getElementsByClass("d-genres").first().text();
            } catch (HttpStatusException | NullPointerException e) {
                LOGGER.debug("Didn't find anything, caught NullPointerException!");
            }
            LOGGER.debug("Genre result found so far = {}", genre);
        }
        return genre;
    }

    /**
     * Собранные ключевые слова превращаем в имеющиеся жанры
     *
     * @param genre
     * @return
     */
    private String[] getGenres(String genre) {
        List<GenreDto> list = genreService.getAllGenreDto();
        Set<String> temp = new HashSet<>();
        String[] genres = genre.toLowerCase().split("[^A-Za-zА-Яа-я0-9]+");
        for (GenreDto dto : list) {
            for (String s : genres) {
                if (dto.getKeywords().contains(s)) {
                    temp.add(dto.getName());
                    break;
                }
            }
            if (temp.size() == 3) {
                break;
            }
        }
        String[] test = temp.toArray(new String[0]);
        return test;
    }
}
