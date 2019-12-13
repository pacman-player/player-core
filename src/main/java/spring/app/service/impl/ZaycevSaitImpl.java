package spring.app.service.impl;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import spring.app.service.abstraction.ZaycevSaitServise;

import java.io.IOException;

@Service
public class ZaycevSaitImpl implements ZaycevSaitServise {

    RestTemplate restTemplate = new RestTemplate();

    @Override
    public void searchSongByAuthorOrSongs(String author, String song) {

        String url = "https://zaycev.net/search.html?query_search=";

      //  Document document = restTemplate.getForObject("https://zaycev.net/search.html?query_search=" + song, Document.class);


        Document document = null;

        try {
            document = Jsoup.connect(url + song).get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Elements playList =  document.select("meta");


        //https://zaycev.net/search.html?query_search=
    }
}
