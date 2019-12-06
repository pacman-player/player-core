package spring.app.service.abstraction;

import spring.app.model.Song;

public interface SongService {
    Song getByName(String name);
}
