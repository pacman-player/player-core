package spring.app.service.abstraction;

import spring.app.model.SongDownloadRequestInfo;

import java.io.IOException;
import java.util.List;

public interface DownloadMusicVkRuService {
    List<SongDownloadRequestInfo> searchSongByAuthorOrSongs(String artist, String track) throws IOException;
    byte[] getSong(long id) throws IOException;
}
