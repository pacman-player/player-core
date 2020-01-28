package spring.app.service.abstraction;

import spring.app.model.Genre;

import java.util.List;

public interface GenreService {

    void addGenre(Genre genre);

    List<Genre> getAllGenre();



    Genre getByName(String name);

    Genre getById(Long id);

    void updateGenre(Genre genre);

    void deleteGenreById(Long id);
}
