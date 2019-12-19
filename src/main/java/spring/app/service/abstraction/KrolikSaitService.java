package spring.app.service.abstraction;

public interface KrolikSaitService {
    String searchSongByAuthorOrSongs(String author, String song);

    byte[] getSong(String avtor, String song);
}
