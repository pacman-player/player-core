package spring.app.service.impl;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import spring.app.model.SongDownloadRequestInfo;
import spring.app.service.abstraction.ZaycevSaitServise;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Service
public class ZaycevSaitServiceImpl implements ZaycevSaitServise {

    private RestTemplate restTemplate = new RestTemplate();

    // @Value("${uploaded_files_path}") - не работает с аннотацией
    private String fileFolder = "D:/songs/";

    public ZaycevSaitServiceImpl() {
    }

    @Override
    public List<SongDownloadRequestInfo> searchSongByAuthorOrSongs(String author, String song) {

        String url = "https://zaycev.net/search.html?query_search=";
        Document document = null;
        String link = "";
        List<SongDownloadRequestInfo> songDownloadRequestInfos = new ArrayList<>();

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
                if (authorAndSong[0].equals("") || authorAndSong[1].equals("")) {
                    continue;
                }
                String authorName = authorAndSong[0];
                String songName = authorAndSong[1];
                link = "https://zaycev.net" + divId.attr("href") + "?spa=false";

//                if (author.toLowerCase().equals(authorAndSong[0]) && song.toLowerCase().equals(authorAndSong[1])) {// как бы автор и песня
//                    link = "https://zaycev.net" + divId.attr("href") + "?spa=false";
//                    if (link.isEmpty()) {
//                        System.err.println("Ссылка пустая!!!");
//                    }
//                    break;
//                }
                    songDownloadRequestInfos.add(new SongDownloadRequestInfo(link,authorName,songName));
            }
        }

        return songDownloadRequestInfos;
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

        String link = searchSongByAuthorOrSongs(author, song).get(0).getLink();
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
