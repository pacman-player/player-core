package spring.app.service.impl;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class KrolikSaitImpl {

    public void searchSongByAuthorOrSongs(String author, String song) {
        String urlSearch = "https://krolik.biz/search/?q=";
        Document document = null;
        Document documentDownload = null;
        String linkDownload = "";
        String link = "";

        try {
            document = Jsoup.connect(urlSearch + author + "+" + song).get();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error search on krolic.biz");
        }

        Elements playList1 = document.getElementsByAttributeValue("class", "mp3");

        //получение ссылки для редеректа на страницускачки
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


        //----------------------

        try {
            documentDownload = Jsoup.connect(linkDownload).get();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error search on krolic.biz");
        }

        System.out.println(documentDownload);

        Elements playList = documentDownload.getElementsByTag("script");


        System.out.println(playList);

        // link = playList.attr("href");
          link = playList.text();

        System.out.println(link);

    }
}
