package spring.app.service.abstraction;

import org.springframework.http.ResponseEntity;
import spring.app.exceptions.DownloadMusicVkRuException;

public interface DownloadMusicVkRuService {
    ResponseEntity<String> search(String artist, String track) throws DownloadMusicVkRuException;
}
