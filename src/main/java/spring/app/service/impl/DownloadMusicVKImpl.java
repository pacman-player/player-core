package spring.app.service.impl;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import spring.app.service.abstraction.DownloadMusicVK;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@PropertySource("classpath:uploadedFilesPath.properties")
public class DownloadMusicVKImpl implements DownloadMusicVK {
    private RestTemplate restTemplate = new RestTemplate();

    // @Value("${uploaded_files_path}") - не работает с аннотацией
    private String fileFolder =  "D:/songs/";

    @Override
    public String searchSongByAuthorOrSongs(String author, String song)  {
        String url = "https://downloadmusicvk.ru/audio/search?q=";
        Document document = null;
        String urlDownload ="";
        try {
            document = Jsoup.connect(url + author + " " + song).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert document != null;
        Elements divElements = document.getElementsByAttributeValue("class", "row audio vcenter");
        for (Element divElement : divElements) {
            Element hrefElement = divElement.child(2);
            urlDownload = getDownloadUrl(hrefElement.child(2).attr("href")  );
            if (urlDownload.isEmpty()) {
                System.err.println("Ссылка пустая!!!");
            }
            break;

        }
        return urlDownload;
    }

    @Override
    public byte[] getSong(String author, String song) {
        String link = searchSongByAuthorOrSongs(author, song);

        byte[] bytes =  restTemplate.getForObject(link, byte[].class);
        //сожранение в папку D:/songs/ для тестирования
        Path path = Paths.get(fileFolder +author +" "+ song+".mp3");
        /*try {
            Files.write(path,bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        return null;
    }

    private String getDownloadUrl(String url){
        Document documentDownload = null;
        try {
            documentDownload = Jsoup.connect("https://downloadmusicvk.ru" +url).get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        assert documentDownload != null;
        Elements docElements = documentDownload.getElementsByAttributeValue("id", "download-audio");
        if (docElements.text().equals("Нельзя качать песни больше 100 МБ. Приносим извинения.")){
            return "";
        }
        return "https://downloadmusicvk.ru" + docElements.attr("href")+".mp3";
    }
}
