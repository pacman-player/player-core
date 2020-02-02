package spring.app.service.impl;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;
import spring.app.service.abstraction.GenreDefinerService;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Класс определяет жанр песни посредством запроса в поисковик google.com
 * если не находит то обращается music.yandex.com
 */
@Service
@Transactional
public class GenreDefinerServiceImpl implements GenreDefinerService {

    @Override
    public String[] defineGenre(String trackName) throws IOException {
        String query1 = trackName;
        String quety2 = " жанр";
        String url = "https://www.google.ru/search?q=" + query1 + quety2;
        String genre = "неизвестно";

        Document doc = Jsoup
                .connect(url)
                .userAgent("Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.117 YaBrowser/20.2.0.1043 Yowser/2.5 Safari/537.36")
                .header("Accept-Language", "ru")
                .header("Accept-Encoding", "gzip,deflate,br")
                .timeout(10000).get();

        try {
            genre = doc.getElementsByClass("Z0LcW").first().text();
        } catch (NullPointerException e) {
        }
        if (genre.equals("неизвестно")) {      //для поиска жанра исполнетелей иностранных песен меняется стиль зап
            quety2 = " genre";
            url = "https://www.google.ru/search?q=" + query1 + quety2 + "&num=10";
            doc = Jsoup
                    .connect(url)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.117 YaBrowser/20.2.0.1043 Yowser/2.5 Safari/537.36")
                    .header("Accept-Language", "ru")
                    .header("Accept-Encoding", "gzip,deflate,br")
                    .timeout(5000).get();

            try {
                genre = doc.getElementsByClass("Z0LcW").first().text();
            } catch (NullPointerException e) {
            }
        }
        if (genre.equals("неизвестно")) {
            genre = defineGenreByAuthor(trackName); //если поиск жанра неудачно через google.ru, пытаемся узнать через music.yandex.com
        }
        return getGenres(genre); //фильтр от "муссора"
    }

    public String defineGenreByAuthor(String trackName) throws IOException {
        String query1 = trackName;
        String url = "https://music.yandex.com/search?text=" + query1 + "&type=artists";
        String genre = "неизвестно";

        Document doc = Jsoup
                .connect(url)
                .userAgent("Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.117 YaBrowser/20.2.0.1043 Yowser/2.5 Safari/537.36")
                .header("Accept-Language", "ru")
                .header("Accept-Encoding", "gzip, deflate, br")
                .timeout(10000).get();

        try {
            genre = doc.getElementsByClass("d-genres").first().text();
        } catch (NullPointerException e) {
        }

        if (genre.equals("неизвестно")) {
            String artistName = query1.split(" – ")[0];
            url = "https://music.yandex.com/search?text=" + artistName + "&type=artists";

            doc = Jsoup
                    .connect(url)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.117 YaBrowser/20.2.0.1043 Yowser/2.5 Safari/537.36")
                    .header("Accept-Language", "ru")
                    .header("Accept-Encoding", "gzip, deflate, br")
                    .timeout(10000).get();

            try {
                genre = doc.getElementsByClass("d-genres").first().text();
            } catch (NullPointerException e) {
            }
        }
        return genre;
    }

    /**
     * Возможны несколько стилей у одной песни.
     * Поисковики выдают различные ответы с различными приписками к жанрам
     * Очищаем от "мусора" и возвращаем массив строк с названием жанров
     * @param genre
     * @return
     */
    public String[] getGenres(String genre) {

        genre = genre.replaceAll("\\s", "");
        Pattern pattern = Pattern.compile("иностранный|русский|иностранная|-музыка|музыка");

        Matcher matcher = pattern.matcher(genre);
        StringBuffer buffer = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(buffer, "");
        }
        matcher.appendTail(buffer);

        pattern = Pattern.compile("/|,");
        String[] genres = pattern.split(buffer);

        return genres;
    }

}
