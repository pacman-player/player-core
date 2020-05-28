package spring.app.service.abstraction;

import spring.app.model.Song;
import spring.app.service.entity.Track;

import java.io.IOException;


public interface MusicSearchService {

    Track getSong(String author, String song) throws IOException;

    Song updateData(Track track) throws IOException;

    //TODO: remove
//    Long updateData(String fullTrackName, String author, String songName) throws IOException;
}
