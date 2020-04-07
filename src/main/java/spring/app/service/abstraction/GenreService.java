package spring.app.service.abstraction;

import spring.app.model.Genre;

import java.sql.Timestamp;
import java.util.List;

public interface GenreService {

    void addGenre(Genre genre);

    void updateGenre(Genre genre);

    void deleteGenreById(Long id);

    Genre getById(Long id);

    Genre getByName(String name);

    List<Genre> getByCreatedDateRange(Timestamp dateFrom, Timestamp dateTo);

    List<Genre> getAllGenre();

    List<Genre> getAllApprovedGenre();
}
