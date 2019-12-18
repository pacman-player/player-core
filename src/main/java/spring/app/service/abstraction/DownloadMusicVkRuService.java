package spring.app.service.abstraction;

import spring.app.exceptions.DownloadMusicVkRuException;

public interface DownloadMusicVkRuService {
    void search(String artist, String track) throws DownloadMusicVkRuException;
}
