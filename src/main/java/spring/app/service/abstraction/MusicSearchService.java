package spring.app.service.abstraction;

import spring.app.service.entity.Track;

import java.io.IOException;

public interface MusicSearchService {

    Track getSong(String author, String song) throws IOException;

    String findSongInfo(String author, String song) throws IOException;

    Long updateData(Track track) throws IOException;
}
