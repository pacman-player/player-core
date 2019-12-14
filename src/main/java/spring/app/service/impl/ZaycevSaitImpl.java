package spring.app.service.impl;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import spring.app.service.abstraction.ZaycevSaitServise;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class ZaycevSaitImpl implements ZaycevSaitServise {

    RestTemplate restTemplate = new RestTemplate();

    @Override
    public Map<String[], String> searchSongByAuthorOrSongs(String author, String song) {

        String url = "https://zaycev.net/search.html?query_search=";
        Document document = null;
        Map<String[], String> aftorSongLink = new HashMap<>();

        try {
            document = Jsoup.connect(url + author + " " + song).get();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error search on zaycev.net");
        }

        Elements playList1 = document.getElementsByClass("musicset-track__download track-geo");

        playList1.forEach(a -> {
            Element divId = a.child(0);
            String title = divId.attr("title");
            String delStr = title.replace("Скачать трек ", "");
            String[] avtorAndSong = delStr.split(" – ", 2);
            String linkOnSong = "https://zaycev.net" + divId.attr("href") + "?spa=false";
            aftorSongLink.put(avtorAndSong, linkOnSong);
        });

        return aftorSongLink;
    }
}
