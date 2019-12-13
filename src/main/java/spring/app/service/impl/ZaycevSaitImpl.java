package spring.app.service.impl;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
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

       // Elements playList =  document.getElementsByClass("musicset-track-list");
      //  Elements playList1 =  document.getElementsByClass("search-page__tracks");//list search!!!
        Elements playList1 =  document.getElementsByClass("musicset-track clearfix ");
        //Elements playList2 =  playList1.

        playList1.forEach(a-> {
            Element divId = a.child(0);
            String idSong = divId.attr("title");
            System.out.println(idSong);
                });

      // System.out.println(playList.html());
      //  System.out.println(playList1.html());
      //  System.out.println(playList1.html());


        //https://zaycev.net/search.html?query_search=
    }
}
