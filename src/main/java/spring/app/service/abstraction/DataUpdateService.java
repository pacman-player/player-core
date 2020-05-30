package spring.app.service.abstraction;

import spring.app.model.Song;

public interface DataUpdateService {
    Song updateData(String author, String song, String[] genreNames);
}
