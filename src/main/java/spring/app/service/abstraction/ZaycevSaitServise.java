package spring.app.service.abstraction;

import java.io.IOException;

public interface ZaycevSaitServise {
    void searchSongByAuthorOrSongs(String author, String song ) throws IOException;
}
