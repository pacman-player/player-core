package spring.app.service.abstraction;

import java.io.IOException;
import java.util.Map;

public interface ZaycevSaitServise {
    Map<String[], String> searchSongByAuthorOrSongs(String author, String song ) throws IOException;
}
