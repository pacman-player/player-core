package spring.app.service.impl;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import spring.app.model.SongDownloadRequestInfo;
import spring.app.service.abstraction.KrolikSaitService;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class KrolikSaitServiceImpl implements KrolikSaitService {

    RestTemplate restTemplate = new RestTemplate();

    public KrolikSaitServiceImpl() {
    }

    @Override
    public List<SongDownloadRequestInfo> searchSongByAuthorOrSongs(String author, String song) {

        String urlSearch = "https://krolik.biz/search/?q=";
        Document document = null;
        Document documentDownload = null;
        Document documentDownload2 = null;
        String linkDownload2 = "";
        String link = "";
        List<SongDownloadRequestInfo> songDownloadRequestInfos = new ArrayList<>();

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
            SongDownloadRequestInfo songDownloadRequestInfo=new SongDownloadRequestInfo();
            Elements mark = div.child(1).select("mark");
            songDownloadRequestInfo.setAuthorName(div.child(1).select("div.artist_name").text());
            for (Element item : mark) {
                {
                    songDownloadRequestInfo.setSongName(mark.last().text().toLowerCase());
                }
                Element a = div.child(0).child(1);
                songDownloadRequestInfo.setLink(a.attr("href") + "&push");
             //   if (author.toLowerCase().equals(authorAndSong[0]) && song.toLowerCase().equals(authorAndSong[1])) {
                    System.out.println("123123123123");
                    songDownloadRequestInfos.add(songDownloadRequestInfo);
                    break;
               // }
            }

        }

        //----------------------1

        //получение ссылки для редеректа 2
//        try {
//            documentDownload = Jsoup.connect(linkDownload).get();
//        } catch (IOException e) {
//            e.printStackTrace();
//            System.err.println("Error search on krolic.biz");
//        }
//
//        Element playList = documentDownload.select("script").get(2);
//
//        Pattern p = Pattern.compile("(?is)window.location=\"(.+?)\"");
//        Matcher m = p.matcher(playList.html());
//
//        while (m.find()) {
//            linkDownload2 = m.group(1);
//            break;
//        }
//
//        //----------------------2
//
//        //ссылка на песню
//        try {
//            documentDownload2 = Jsoup.connect(linkDownload2).get();
//        } catch (IOException e) {
//            e.printStackTrace();
//            System.err.println("Error search on krolic.biz");
//        }
//
//        link = documentDownload2.getElementsByClass("btn").attr("href");

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
