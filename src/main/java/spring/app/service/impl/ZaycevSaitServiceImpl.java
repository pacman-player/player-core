package spring.app.service.impl;

import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import spring.app.service.abstraction.ZaycevSaitServise;
import spring.app.util.PlayerPaths;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
@Transactional
public class ZaycevSaitServiceImpl implements ZaycevSaitServise {

    private RestTemplate restTemplate = new RestTemplate();
    //    private String[] authorAndSong;
    private String title;

    @Override
    public String searchSongByAuthorOrSongs(String author, String song) {

        String baseUrl = "https://zaycev.net";
        String searchUrl = "https://zaycev.net/search.html?query_search=";
        Document document = null;
        String link = "";

        try {
            document = Jsoup.connect(searchUrl + author + " " + song).get();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error search on zaycev.net");
        }

        Element div = document.getElementsByClass("musicset-track clearfix ").get(0);
        String jsonUrl = div.attr("data-url");
        Element div2 = div.getElementsByClass("musicset-track__fullname").get(0);
        title = div2.attr("title");

//        if (title != null) {
//            authorAndSong = title.split(" – ");
//        }

        try {
            String json = Jsoup.connect(baseUrl + jsonUrl).ignoreContentType(true).execute().body();
            JSONObject jsonObj = new JSONObject(json);
            link = jsonObj.getString("url");
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
        return link;
    }
//    @Override
//    public String searchSongByAuthorOrSongs(String author, String song) {
//
//        String url = "https://zaycev.net/search.html?query_search=";
//        Document document = null;
//        String link = "";
//
//        try {
//            document = Jsoup.connect(url + author + " " + song).get();
//        } catch (IOException e) {
//            e.printStackTrace();
//            System.err.println("Error search on zaycev.net");
//        }
//
//        Elements playList1 = document.getElementsByClass("musicset-track__download track-geo");
//
//        for (Element div : playList1) {
//            Element divId = div.child(0);
//            String title = divId.attr("title");
//
//            if (title != null) {
//                String delStr = title.replace("Скачать трек ", "").toLowerCase();
//                String[] authorAndSong = delStr.split(" – ");
//
//                if (author.toLowerCase().equals(authorAndSong[0]) && song.toLowerCase().equals(authorAndSong[1])) {
//                    link = "https://zaycev.net" + divId.attr("href") + "?spa=false";
//                    if (link.isEmpty()){
//                        System.err.println("Ссылка пустая!!!");
//                    }
//                    break;
//                }
//            }
//        }
//        return link;
//    }


    @Override
    public byte[] getSong(String author, String song) {
        String link = searchSongByAuthorOrSongs(author, song);
        byte[] bytes = restTemplate.getForObject(link, byte[].class);

//        if (authorAndSong.length > 0) {
//            author = authorAndSong[0];
//            song = authorAndSong[1];
//        }

        Path path = PlayerPaths.getSongsDir(title + ".mp3");
        if (path != null) {
            try {
                Files.write(path, bytes);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return bytes;
    }

    @Override
    public String getTrackName() {
        return title;
    }
}
