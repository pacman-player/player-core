package spring.app.service.impl;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import spring.app.service.abstraction.SearchService;

import java.io.IOException;

@Service
@PropertySource("classpath:uploadedFilesPath.properties")
public class SearchServiceImpl implements SearchService {

    private final String SEARCH_BASE_URL = "https://downloadmusicvk.ru/audio/search?q=";
    private final String DOWNLOAD_BASE_URL = "https://downloadmusicvk.ru/audio/play?";

    private final RestTemplate restTemplate;

    @Autowired
    public SearchServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public void search(String artist, String track) {
        Document document = null;
        String searchUrl = SEARCH_BASE_URL + artist.trim().toLowerCase().replaceAll("\\s", "+") +
                "+" + track.trim().toLowerCase().replaceAll("\\s", "+");

        try {
            document = Jsoup.connect(searchUrl).get();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error search on downloadmusicvk.ru");
        }


    }
}