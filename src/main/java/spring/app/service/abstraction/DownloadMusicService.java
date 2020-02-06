package spring.app.service.abstraction;

import spring.app.service.entity.Track;

import java.io.IOException;

public interface DownloadMusicService {

    Track getSong(String author, String song) throws IOException;

}
