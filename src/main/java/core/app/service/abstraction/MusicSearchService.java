package core.app.service.abstraction;

import core.app.service.entity.Track;

import java.io.IOException;


public interface MusicSearchService {

    Track getSong(String author, String song) throws IOException;

    Long updateData(Track track) throws IOException;

    Long updateData(String fullTrackName, String author, String songName) throws IOException;
}
