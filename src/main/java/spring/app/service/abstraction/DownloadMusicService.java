package spring.app.service.abstraction;

import java.io.IOException;

public interface DownloadMusicService {

    String searchSong(String author, String song) throws IOException;

    byte[] getSong(String author, String song) throws IOException;

    String getTrackName();

}
