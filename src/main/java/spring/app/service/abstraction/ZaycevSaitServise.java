package spring.app.service.abstraction;

public interface ZaycevSaitServise {

    String searchSongByAuthorOrSongs(String author, String song);

    byte[] getSong(String avtor, String song);

}
