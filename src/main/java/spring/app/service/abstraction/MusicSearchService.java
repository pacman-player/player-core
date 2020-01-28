package spring.app.service.abstraction;

import java.io.IOException;

public interface MusicSearchService {

    byte[] getSong(String author, String song) throws IOException;

    String getTrackName();
}
