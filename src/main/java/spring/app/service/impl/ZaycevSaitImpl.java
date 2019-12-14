package spring.app.service.impl;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import spring.app.service.abstraction.MusicService;
import spring.app.service.abstraction.ZaycevSaitServise;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class ZaycevSaitImpl implements ZaycevSaitServise {

    private RestTemplate restTemplate = new RestTemplate();

   // @Value("${uploaded_files_path}")
    private String fileFolder = "D:/songs/";

    @Override
    public String searchSongByAuthorOrSongs(String author, String song) {

        String url = "https://zaycev.net/search.html?query_search=";
        Document document = null;
        final String[] link = new String[1];

        try {
            document = Jsoup.connect(url + author + " " + song).get();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error search on zaycev.net");
        }

        Elements playList1 = document.getElementsByClass("musicset-track__download track-geo");

        for (Element a : playList1) {
            Element divId = a.child(0);
            String title = divId.attr("title");

            if (title != null) {
                String delStr = title.replace("Скачать трек ", "").toLowerCase();
                String[] authorAndSong = delStr.split(" – ");

                if (author.toLowerCase().equals(authorAndSong[0]) && song.toLowerCase().equals(authorAndSong[1])) {
                    link[0] = "https://zaycev.net" + divId.attr("href") + "?spa=false";
                    break;
                }

            }

        }

        return link[0];
    }


    @Override
    public byte[] getSong(String author, String song) {

        String link = searchSongByAuthorOrSongs(author, song);
        byte[] bytes =  restTemplate.getForObject(link,byte[].class);

        //сожранение в папку D:/songs/ для тестирования
        Path path = Paths.get(fileFolder +author +" "+ song+".mp3");
        try {
            Files.write(path,bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bytes;

    }

}
