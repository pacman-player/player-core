package spring.app.service.impl;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import spring.app.service.abstraction.MusicService;
import spring.app.service.abstraction.ZaycevSaitServise;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

@Service
public class ZaycevSaitServiceImpl implements ZaycevSaitServise {

    private RestTemplate restTemplate = new RestTemplate();

   // @Value("${uploaded_files_path}") - не работает с аннотацией
    private String fileFolder =  "D:/songs/";

    @Override
    public String searchSongByAuthorOrSongs(String author, String song) {

        String url = "https://zaycev.net/search.html?query_search=";
        Document document = null;
        String link = "";

        try {
            document = Jsoup.connect(url + author + " " + song).get();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error search on zaycev.net");
        }

        Elements playList1 = document.getElementsByClass("musicset-track__download track-geo");

        for (Element div : playList1) {
            Element divId = div.child(0);
            String title = divId.attr("title");

            if (title != null) {
                String delStr = title.replace("Скачать трек ", "").toLowerCase();
                String[] authorAndSong = delStr.split(" – ");

                if (author.toLowerCase().equals(authorAndSong[0]) && song.toLowerCase().equals(authorAndSong[1])) {
                    link = "https://zaycev.net" + divId.attr("href") + "?spa=false";
                    if (link.isEmpty()){
                        System.err.println("Ссылка пустая!!!");
                    }
                    break;
                }
            }
        }

        return link;
    }


    @Override
    public byte[] getSong(String author, String song) {

        FileInputStream fis;
        Properties property = new Properties();

        try {
            fis = new FileInputStream("src/main/resources/uploadedFilesPath.properties");
            property.load(fis);
        } catch (IOException e) {
            System.err.println("ОШИБКА: Файл свойств отсуствует!");
        }

        String link = searchSongByAuthorOrSongs(author, song);
        byte[] bytes = restTemplate.getForObject(link, byte[].class);

        Path path = Paths.get(property.getProperty("downloadSongsPath") + author + " " + song + ".mp3");
        try {
            Files.write(path, bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bytes;
    }

}
