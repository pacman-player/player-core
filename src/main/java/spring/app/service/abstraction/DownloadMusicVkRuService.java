package spring.app.service.abstraction;

import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public interface DownloadMusicVkRuService {
    ResponseEntity<String> search(String artist, String track) throws IOException, UnsupportedEncodingException;
}
