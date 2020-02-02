package spring.app.service.abstraction;

public interface DataUpdateService {
    Long updateData(String author, String song, String[] genreNames);
}
