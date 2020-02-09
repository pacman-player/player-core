package spring.app.service.abstraction;

import spring.app.service.entity.Track;

import java.io.IOException;

public interface FindMusicInDBService {

    Track findTrackFromDB(String trackName) throws IOException;
}
