package spring.app.service.abstraction;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface ZaycevSaitServise {

    String searchSongByAuthorOrSongs(String author, String song);

    byte[] getSong(String avtor, String song);

}
