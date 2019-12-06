package spring.app.service.abstraction;

import spring.app.model.Genre;

public interface GenreService {
    void addGenre(Genre genre);
    Genre getByName(String name);
}
