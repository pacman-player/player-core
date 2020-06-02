package spring.app.service.abstraction;

import java.io.IOException;

public interface GenreDefinerService {
    String[] defineGenre(String trackAuthor) throws IOException;
}
