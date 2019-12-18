package spring.app.service.impl;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestTemplate;
import spring.app.service.abstraction.SearchService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

@Service
public class SearchServiceImpl implements SearchService {

    private final String SEARCH_BASE_URL = "https://downloadmusicvk.ru/audio/search?q=";
    private final String DOWNLOAD_BASE_URL = "https://downloadmusicvk.ru/audio/play?";

    @Value("${uploaded_files_path}")
    private String fileFolder;

    private final RestTemplate restTemplate;

    @Autowired
    public SearchServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public void search(String artist, String track) throws IOException {
        String searchUrl = SEARCH_BASE_URL + artist.trim().toLowerCase().replaceAll("\\s", "+") +
                "+" + track.trim().toLowerCase().replaceAll("\\s", "+");

        String downloadUrl = "";
        Document document = Jsoup.connect(searchUrl).get();
        //добавить проверку document на null
        Elements divElements = document.getElementsByAttributeValue("class", "row audio vcenter");
        for (Element divElement : divElements) {
            Element hrefElement = divElement.child(2);
            downloadUrl = getDownloadUrl(hrefElement.child(2).attr("href"));
            if (downloadUrl.isEmpty()) {
                System.err.println("Ссылка пустая!!!");
            }
            break;
        }

        /*HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_OCTET_STREAM));
        headers.set("Content-Type","application/json");
        headers.set("user-agent", "PostmanRuntime/7.20.1");
        headers.set("COOKIE", "__cfduid=d9497a0b3f24a74b42b4cfabb808a31681576589130");
        headers.set("HOST", "downloadmusicvk.ru");
        headers.set("CONNECTION", "keep-alive");
        HttpEntity entity = new HttpEntity(headers);

        byte[] bytes = restTemplate.exchange(downloadUrl, HttpMethod.GET, entity, byte[].class).getBody();
        //byte[] bytes = restTemplate.getForObject(downloadUrl, byte[].class);
        Path path = Paths.get(fileFolder + artist + " " + track + ".mp3");
        try {
            Files.write(path, bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        RequestCallback requestCallback = request -> {
            request.getHeaders().clear();
            //request.getHeaders().set("user-agent", "PostmanRuntime/7.20.1");
            request.getHeaders()
                    .setAccept(Arrays.asList(MediaType.APPLICATION_OCTET_STREAM, MediaType.ALL));
            request.getHeaders().set("HOST", "downloadmusicvk.ru");
            request.getHeaders().set("Cookie", "__cfduid=d9497a0b3f24a74b42b4cfabb808a31681576589130");
        };

        // Streams the response instead of loading it all in memory
        ResponseExtractor<Void> responseExtractor = response -> {
            // Here I write the response to a file but do what you like
            Path path = Paths.get(fileFolder + artist + " " + track + ".mp3");
            Files.copy(response.getBody(), path);
            return null;
        };
        restTemplate.execute(downloadUrl, HttpMethod.GET, requestCallback, responseExtractor);
    }

    private String getDownloadUrl(String url){
        Document documentDownload = null;
        try {
            documentDownload = Jsoup.connect("https://downloadmusicvk.ru" + url).get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Elements docElements = documentDownload.getElementsByAttributeValue("id", "download-audio");
        if (docElements.text().equals("Нельзя качать песни больше 100 МБ. Приносим извинения.")){
            return "";
        }
        return "https://downloadmusicvk.ru" + docElements.attr("href");
    }
}