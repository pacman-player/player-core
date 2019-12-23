package spring.app.service.impl;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import spring.app.service.abstraction.KrolikSaitService;
import spring.app.util.PlayerPaths;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class KrolikSaitServiceImpl implements KrolikSaitService {

    private RestTemplate restTemplate = new RestTemplate();

    @Override
    public String searchSongByAuthorOrSongs(String author, String song) {

        String urlSearch = "https://krolik.biz/search/?q=";
        Document document = null;
        Document documentDownload = null;
        Document documentDownload2 = null;
        String linkDownload = "";
        String linkDownload2 = "";
        String link = "";

        try {
            document = Jsoup.connect(urlSearch + author + "+" + song).get();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error search on krolic.biz");
        }

        Elements playList1 = document.getElementsByAttributeValue("class", "mp3");

        //получение ссылки для редеректа 1
        //прверка на совпадение  автора и песни
        for (Element div : playList1) {
            Elements mark = div.child(1).select("mark");
            String[] authorAndSong = new String[3];
            for (Element item : mark) {
                {
                    authorAndSong[0] = mark.first().text().toLowerCase();
                    authorAndSong[1] = mark.last().text().toLowerCase();
                }
                Element a = div.child(0).child(1);
                authorAndSong[2] = a.attr("href");
                if (author.toLowerCase().equals(authorAndSong[0]) && song.toLowerCase().equals(authorAndSong[1])) {
                    linkDownload = authorAndSong[2];
                    break;
                }
            }

        }

        //----------------------1

        //получение ссылки для редеректа 2
        try {
            documentDownload = Jsoup.connect(linkDownload).get();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error search on krolic.biz");
        }

        Element playList = documentDownload.select("script").get(2);

        Pattern p = Pattern.compile("(?is)window.location=\"(.+?)\"");
        Matcher m = p.matcher(playList.html());

        while (m.find()) {
            linkDownload2 = m.group(1);
            break;
        }

        //----------------------2

        //ссылка на песню
        try {
            documentDownload2 = Jsoup.connect(linkDownload2).get();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error search on krolic.biz");
        }

        link = documentDownload2.getElementsByClass("btn").attr("href");

        return link;

    }

    @Override
    public byte[] getSong(String author, String song) {
        String link = searchSongByAuthorOrSongs(author, song);
        byte[] bytes = restTemplate.getForObject(link, byte[].class);

        Path path = PlayerPaths.getSongsDir(author + " " + song + ".mp3");
        try {
            assert path != null;
            Files.write(path, bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bytes;
    }
}
