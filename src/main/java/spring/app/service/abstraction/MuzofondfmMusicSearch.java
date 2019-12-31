package spring.app.service.abstraction;

import spring.app.service.entity.Track;

import java.util.List;

public interface MuzofondfmMusicSearch {

    List<Track> searchSong(String author, String track);

    byte[] getSong(Track track);
}
