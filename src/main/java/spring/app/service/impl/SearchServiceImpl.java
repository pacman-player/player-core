package spring.app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import spring.app.service.abstraction.SearchService;

@Service
public class SearchServiceImpl implements SearchService {

    private final String BASE_URL = "https://api.spotify.com/v1/search?";

    private final RestTemplate restTemplate;

    @Autowired
    public SearchServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public void search(String artist, String track) {
    }
}