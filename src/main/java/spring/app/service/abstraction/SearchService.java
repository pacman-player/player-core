package spring.app.service.abstraction;

import java.io.IOException;

public interface SearchService {
    void search(String artist, String track) throws IOException;
}
