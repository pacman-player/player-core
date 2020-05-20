package spring.app.service.abstraction;

import spring.app.service.entity.Track;

import java.io.IOException;

public interface LoadSongService {
    Track getSong(Long songId) throws IOException;
}
