package spring.app.service.abstraction;

import spring.app.model.SongDownloadRequestInfo;

import java.util.List;

public interface ZaycevSaitServise {

    List<SongDownloadRequestInfo> searchSongByAuthorOrSongs(String author, String song);

    byte[] getSong(String avtor, String song);

}
